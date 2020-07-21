package com.github.linyuzai.janalyzer.markdown.word.handler;

import com.github.linyuzai.janalyzer.markdown.word.DocxReader;
import org.apache.poi.xwpf.usermodel.*;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class DocxTableMarkdownHandler implements ElementHandler {

    @Override
    public boolean support(DocxReader reader, Object element) {
        return element instanceof XWPFTable;
    }

    @Override
    public String handle(DocxReader reader, Object element) {
        StringBuilder builder = new StringBuilder();
        int cellCount = 0;
        int i = 0;
        List<XWPFTableRow> rows = ((XWPFTable) element).getRows();
        for (XWPFTableRow row : rows) {
            if (row == null) {
                continue;
            }
            List<XWPFTableCell> cells = row.getTableCells();
            if (i == 0) {
                cellCount = cells.size();
            }
            if (cellCount == 0) {
                continue;
            }
            List<String> texts = new ArrayList<>(cellCount);
            for (XWPFTableCell cell : cells) {
                if (cell == null) {
                    texts.add("");
                    continue;
                }
                texts.add(reader.read(cell.getBodyElements(), false));
            }
            compensateCell(texts, cellCount);
            builder.append(parseTableRow(texts)).append("\n");
            if (i == 0) {
                List<String> header = new ArrayList<>(cellCount);
                for (int j = 0; j < cellCount; j++) {
                    header.add("-");
                }
                builder.append(parseTableRow(header)).append("\n");
            }
            i++;
        }
        return builder.toString();
    }

    public String parseTableRow(List<String> cells) {
        StringJoiner joiner = new StringJoiner("|", "|", "|");
        for (String cell : cells) {
            joiner.add(cell);
        }
        return joiner.toString();
    }

    public void compensateCell(List<String> cells, int cellCount) {
        if (cellCount > cells.size()) {
            for (int i = 0; i < cellCount - cells.size(); i++) {
                cells.add("");
            }
        }
    }
}
