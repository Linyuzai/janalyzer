package com.github.linyuzai.janalyzer.markdown.analyzer;

import com.github.linyuzai.janalyzer.markdown.analyzer.proxy.AbstractAnalyzerProxy;
import com.github.linyuzai.janalyzer.markdown.context.MarkdownContext;
import com.github.linyuzai.janalyzer.markdown.element.BlockCodeElement;

public class BlockCodeAnalyzer extends MarkdownAnalyzer {

    public static class Proxy extends AbstractAnalyzerProxy<BlockCodeAnalyzer> {

        private static final Proxy instance = new Proxy(new BlockCodeAnalyzer());

        public static Proxy getInstance() {
            return instance;
        }

        public Proxy(BlockCodeAnalyzer analyzer) {
            super(analyzer);
        }
    }

    @Override
    public void registerSelfAnalyzers() {
        registerAnalyzer(TextareaAnalyzer.Proxy.getInstance());
    }

    @Override
    public BlockCodeElement analyze(MarkdownContext context) {
        String source = context.getSource();
        boolean loop = context.isLoop();
        String mask = "```";
        String s = trimStart(source, ' ');
        int blankLength = source.length() - s.length();
        if (s.startsWith(mask)) {
            int beginIndex = 0;
            int endIndex = s.indexOf("```\n", mask.length());
            int endLength = "```\n".length();
            if (endIndex == -1) {
                endIndex = s.indexOf("```", mask.length());
                endLength = "```".length();
            }
            if (endIndex == -1) {
                return null;
            }
            String content = s.substring(beginIndex, endIndex + endLength);
            int nFirstIndex = content.indexOf("\n");
            if (nFirstIndex == -1) {
                return null;
            }
            String language = content.substring(mask.length(), nFirstIndex);
            String inner = content.substring(nFirstIndex + 1, content.length() - endLength);
            BlockCodeElement blockCodeElement = (BlockCodeElement) super.analyze(newContext(inner, loop));
            baseElement(blockCodeElement, beginIndex, endIndex + endLength + blankLength, content);
            blockCodeElement.setLanguage(language);
            return blockCodeElement;

        }
        return null;
    }

    @Override
    public BlockCodeElement newElement() {
        return new BlockCodeElement();
    }

}
