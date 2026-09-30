package br.com.generic.controller;

import jakarta.ws.rs.core.UriInfo;

public abstract class BaseController {

    protected final UriInfo uriInfo;

    public BaseController(final UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }

    protected UriInfo getUriInfo() {
        return uriInfo;
    }
}
