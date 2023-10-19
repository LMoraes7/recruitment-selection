package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.selective.process.response;

public class SelectiveProcessResponse {
    private String id;
    private String title;

    public SelectiveProcessResponse(final String id, final String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
