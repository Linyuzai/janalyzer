package com.github.linyuzai.janalyzer.markdown.word.handler;

import com.github.linyuzai.janalyzer.markdown.word.DocxReader;
import org.apache.poi.xwpf.usermodel.IBodyElement;

public interface BodyElementHandler {

    boolean support(DocxReader reader, IBodyElement element);

    String handle(DocxReader reader, IBodyElement element);
}
