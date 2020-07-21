package com.github.linyuzai.janalyzer.markdown.word.handler;

import com.github.linyuzai.janalyzer.markdown.word.DocxReader;

public interface ElementHandler {

    boolean support(DocxReader reader, Object element);

    String handle(DocxReader reader, Object element);
}
