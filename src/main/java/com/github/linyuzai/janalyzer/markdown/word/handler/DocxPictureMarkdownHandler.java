package com.github.linyuzai.janalyzer.markdown.word.handler;

import com.github.linyuzai.janalyzer.markdown.word.DocxReader;
import org.apache.poi.xwpf.usermodel.*;

public class DocxPictureMarkdownHandler implements ElementHandler {

    private boolean showFilename;

    private UrlProvider urlProvider;

    public DocxPictureMarkdownHandler(UrlProvider urlProvider) {
        this(urlProvider, false);
    }

    public DocxPictureMarkdownHandler(UrlProvider urlProvider, boolean showFilename) {
        this.urlProvider = urlProvider;
        this.showFilename = showFilename;
    }

    @Override
    public boolean support(DocxReader reader, Object element) {
        return element instanceof XWPFPicture;
    }

    @Override
    public String handle(DocxReader reader, Object element) {
        XWPFPictureData pictureData = ((XWPFPicture) element).getPictureData();
        byte[] bytes = pictureData.getData();
        String filename = pictureData.getFileName();
        int type = pictureData.getPictureType();
        String url = urlProvider.getUrl(bytes, filename, getContentType(type));
        return "![" + (showFilename ? filename : "") + "](" + url + ")";
    }

    public String getContentType(int type) {
        //TODO 添加ContentType
        return "";
    }

    public UrlProvider getUrlProvider() {
        return urlProvider;
    }

    public void setUrlProvider(UrlProvider urlProvider) {
        this.urlProvider = urlProvider;
    }

    public boolean isShowFilename() {
        return showFilename;
    }

    public void setShowFilename(boolean showFilename) {
        this.showFilename = showFilename;
    }

    public interface UrlProvider {

        String getUrl(byte[] bytes, String filename, String contentType);
    }
}
