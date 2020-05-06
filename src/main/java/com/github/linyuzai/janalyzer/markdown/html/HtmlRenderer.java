package com.github.linyuzai.janalyzer.markdown.html;

import com.github.linyuzai.janalyzer.markdown.element.*;
import com.github.linyuzai.janalyzer.markdown.word.DocxWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

public class HtmlRenderer {

    private MarkdownElement markdownElement;

    private String content;

    private String charset = "utf-8";

    private String title = "";

    public HtmlRenderer(MarkdownElement markdownElement) {
        this.markdownElement = markdownElement;
    }

    public HtmlRenderer charset(String charset) {
        this.charset = charset;
        return this;
    }

    public HtmlRenderer title(String title) {
        this.title = title;
        return this;
    }

    public DocxWriter docx() {
        return new DocxWriter(content).charset(charset);
    }

    public HtmlRenderer render() {
        content = _doctype(_html(_head(_body(render0(markdownElement, 0)))));
        return this;
    }

    private String _doctype(String html) {
        return "<!DOCTYPE html>\n" + html;
    }

    private String _html(String html) {
        return "<html lang=\"en\">\n" + html + "</html>";
    }

    private String _head(String html) {
        return "<head>\n" + _charset() + _title() + _style() + "</head>\n" + html;
    }

    private String _charset() {
        return "<meta charset=\"" + charset + "\">\n";
    }

    private String _title() {
        return "<title>" + title + "</title>\n";
    }

    private String _style() {
        return "<style type=\"text/css\">"
                + "code { padding : 2px 4px;\ncolor : #c0341d;\nbackground-color : #fbe5e1;\nborder-radius : 4px}"
                + "</style>";
    }

    private String _body(String html) {
        return "<body>\n" + html + "</body>\n";
    }

    public HtmlRenderer write(String path) throws IOException {
        return write(new File(path));
    }

    public HtmlRenderer write(File file) throws IOException {
        File parent = file.getParentFile();
        if (!parent.exists()) {
            if (!parent.mkdirs()) {
                throw new RuntimeException(parent.getAbsolutePath() + " mkdirs failure");
            }
        }
        if (!file.exists()) {
            if (!file.createNewFile()) {
                throw new RuntimeException(file.getAbsolutePath() + " create new file failure");
            }
        }
        FileWriter writer = new FileWriter(file);
        writer.write(content);
        writer.flush();
        writer.close();
        return this;
    }

    private String render0(MarkdownElement element, int deep) {
        Collection<MarkdownElement> elements = element.getChildren();
        if (elements == null) {
            return "";
        }
        deep++;
        StringBuilder builder = new StringBuilder();
        for (MarkdownElement e : elements) {
            if (deep == 1) {
                builder.append("<p>");
            }
            if (e instanceof BlankLineElement) {
                //builder.append("\n");
            } else if (e instanceof BlockCodeElement) {
                builder.append("<pre lang='").append(((BlockCodeElement) e).getLanguage()).append("'>");
                builder.append(render0(e, deep));
                builder.append("</pre>");
            } else if (e instanceof CombinationElement) {
                builder.append(render0(e, deep));
            } else if (e instanceof DividerElement) {
                builder.append("<hr/>");
            } else if (e instanceof HeaderElement) {
                int headerLevel = ((HeaderElement) e).getHeaderLevel();
                builder.append("<h").append(headerLevel).append(">");
                builder.append(render0(e, deep));
                builder.append("</h").append(headerLevel).append(">");
            } else if (e instanceof OrderedItemElement) {
                builder.append("<li>");
                builder.append(render0(e, deep));
                builder.append("</li>");
            } else if (e instanceof OrderedListElement) {
                builder.append("<ol>");
                builder.append(render0(e, deep));
                builder.append("</ol>");
            } else if (e instanceof ReferenceElement) {
                builder.append("<blockquote>");
                builder.append(render0(e, deep));
                builder.append("</blockquote>");
            } else if (e instanceof TableElement) {
                builder.append("<table>");
                builder.append(render0(e, deep));
                builder.append("</table>");
            } else if (e instanceof TableCellElement) {
                builder.append("<td>");
                builder.append(render0(e, deep));
                builder.append("</td>");
            } else if (e instanceof TableRowElement) {
                builder.append("<tr>");
                builder.append(render0(e, deep));
                builder.append("</tr>");
            } else if (e instanceof TextElement) {
                builder.append(((TextElement) e).getText());
            } else if (e instanceof UnorderedItemElement) {
                builder.append("<li>");
                builder.append(render0(e, deep));
                builder.append("</li>");
            } else if (e instanceof UnorderedListElement) {
                builder.append("<ul>");
                builder.append(render0(e, deep));
                builder.append("</ul>");
            } else if (e instanceof BoldElement) {
                builder.append("<strong>");
                builder.append(render0(e, deep));
                builder.append("</strong>");
            } else if (e instanceof DeleteLineElement) {
                builder.append("<del>");
                builder.append(render0(e, deep));
                builder.append("</del>");
            } else if (e instanceof HyperlinkElement) {
                builder.append("<a href='").append(((HyperlinkElement) e).getUrl()).append("'>");
                builder.append(render0(e, deep));
                builder.append("</a>");
            } else if (e instanceof InlineCodeElement) {
                builder.append("<code>");
                builder.append(render0(e, deep));
                builder.append("</code>");
            } else if (e instanceof ItalicElement) {
                builder.append("<em>");
                builder.append(render0(e, deep));
                builder.append("</em>");
            } else if (e instanceof ItalicBoldElement) {
                builder.append("<em><strong>");
                builder.append(render0(e, deep));
                builder.append("</strong></em>");
            } else if (e instanceof PictureElement) {
                builder.append("<img src='").append(((PictureElement) e).getUrl()).append("'>");
                builder.append(render0(e, deep));
                builder.append("</img>");
            }
            if (deep == 1) {
                builder.append("</p>\n");
            }
        }
        return builder.toString();
    }

    public String getContent() {
        return content;
    }
}