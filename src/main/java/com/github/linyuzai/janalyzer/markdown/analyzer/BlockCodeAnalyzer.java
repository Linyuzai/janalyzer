package com.github.linyuzai.janalyzer.markdown.analyzer;

import com.github.linyuzai.janalyzer.markdown.context.MarkdownContext;
import com.github.linyuzai.janalyzer.markdown.element.BlockCodeElement;

public class BlockCodeAnalyzer extends MarkdownAnalyzer {

    private static final BlockCodeAnalyzer instance = new BlockCodeAnalyzer();

    public static BlockCodeAnalyzer getInstance() {
        return instance;
    }

    BlockCodeAnalyzer() {

    }

    @Override
    public void registerSelfAnalyzers() {
        registerAnalyzer(TextAnalyzer.getInstance());
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
            blockCodeElement.setContent(content);
            blockCodeElement.setBeginIndex(beginIndex);
            blockCodeElement.setEndIndex(endIndex + endLength + blankLength);
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
