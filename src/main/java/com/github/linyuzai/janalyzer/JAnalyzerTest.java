package com.github.linyuzai.janalyzer;

import com.github.linyuzai.janalyzer.markdown.MarkdownReader;
import com.github.linyuzai.janalyzer.markdown.analyzer.MarkdownAnalyzer;

import java.io.IOException;
import java.util.Arrays;

public class JAnalyzerTest {

    public static void main(String[] args) throws IOException {
        //MarkdownAnalyzer.getInstance().registerAnalyzers();
        //System.out.println(Arrays.toString("af".split("\\|", -1)));
        new MarkdownReader().file("/Users/linyuzai/IdeaProjects/Landsky/Janalyzer/TEST.md").read()
                .html().render()
                .docx().write("/Users/linyuzai/IdeaProjects/Landsky/Janalyzer/TEST.docx");
                //.html()
                //.render()
                //.write("/Users/linyuzai/IdeaProjects/Landsky/Janalyzer/TEST.html");
        //.docx("/Users/linyuzai/IdeaProjects/Landsky/Janalyzer/TEST.docx");
    }
}
