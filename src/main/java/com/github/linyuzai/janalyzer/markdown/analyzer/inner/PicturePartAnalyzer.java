package com.github.linyuzai.janalyzer.markdown.analyzer.inner;

import com.github.linyuzai.janalyzer.markdown.analyzer.CombinationAnalyzer;
import com.github.linyuzai.janalyzer.markdown.analyzer.holder.AbstractAnalyzerHolder;
import com.github.linyuzai.janalyzer.markdown.context.MarkdownContext;
import com.github.linyuzai.janalyzer.markdown.element.PictureElement;

public class PicturePartAnalyzer extends InnerPartAnalyzer {

    public static class Holder extends AbstractAnalyzerHolder<PicturePartAnalyzer> {

        private static final PicturePartAnalyzer.Holder instance = new PicturePartAnalyzer.Holder();

        public static PicturePartAnalyzer.Holder getInstance() {
            return instance;
        }

        private Holder() {
        }

        @Override
        public PicturePartAnalyzer newAnalyzer() {
            return new PicturePartAnalyzer();
        }
    }

    private boolean analyseName = false;

    public boolean isAnalyseName() {
        return analyseName;
    }

    public void setAnalyseName(boolean analyseName) {
        this.analyseName = analyseName;
    }

    @Override
    public void registerSelfAnalyzers() {
        registerAnalyzer(CombinationAnalyzer.Holder.getInstance());
    }

    @Override
    public PictureElement analyze(MarkdownContext context) {
        String source = context.getSource();
        boolean loop = context.isLoop();
        String picFirst = "![";
        String picSecond = "](";
        String picThird = ")";
        int picFirstIndex = source.indexOf(picFirst);
        if (picFirstIndex != -1) {
            int picSecondIndex = source.indexOf(picSecond, picFirstIndex + picFirst.length());
            if (picSecondIndex != -1) {
                int picThirdIndex = source.indexOf(picThird, picSecondIndex + picSecond.length());
                if (picThirdIndex != -1) {
                    int firstBeginIndex = getFirstBeginIndex(context.getParent(), source);
                    if (firstBeginIndex != -1 && firstBeginIndex < picFirstIndex) {
                        return null;
                    }
                    int beginIndex = picFirstIndex;
                    int endIndex = picThirdIndex + picThird.length();
                    String content = source.substring(beginIndex, endIndex);
                    String inner = source.substring(picFirstIndex + picFirst.length(), picSecondIndex);
                    String urlAndTitle = source.substring(picSecondIndex + picSecond.length(), picThirdIndex);
                    PictureElement pictureElement = analyseName ?
                            (PictureElement) super.analyze(newContext(inner, loop)) : newElement();
                    baseElement(pictureElement, beginIndex, endIndex, content);
                    int titleIndex = urlAndTitle.indexOf(" ");
                    if (titleIndex == -1) {
                        pictureElement.setUrl(urlAndTitle);
                    } else {
                        String url = urlAndTitle.substring(0, titleIndex);
                        String title = urlAndTitle.substring(titleIndex);
                        pictureElement.setUrl(url);
                        pictureElement.setTitle(trimBoth(title, ' '));
                    }
                    return pictureElement;
                }
            }
        }
        return null;
    }

    @Override
    public PictureElement newElement() {
        return new PictureElement();
    }
}
