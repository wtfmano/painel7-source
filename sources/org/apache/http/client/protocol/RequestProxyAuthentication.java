package org.apache.http.client.protocol;

import java.io.IOException;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.protocol.HttpContext;

@Deprecated
/* loaded from: classes.dex */
public class RequestProxyAuthentication implements HttpRequestInterceptor {
    public RequestProxyAuthentication() {
        throw new RuntimeException("Stub!");
    }

    @Override // org.apache.http.HttpRequestInterceptor
    public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
        throw new RuntimeException("Stub!");
    }
}