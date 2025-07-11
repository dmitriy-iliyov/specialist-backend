package com.aidcompass.core.security.xss;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.extern.log4j.Log4j2;
import org.owasp.encoder.Encode;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


@Log4j2
public class XssHttRequestWrapper extends HttpServletRequestWrapper {


    public XssHttRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String[] getParameterValues(String name) {
        String [] values = super.getParameterValues(name);
        if (values == null)
            return null;
        int count = values.length;
        String [] shieldedValues = new String[count];
        for (int i = 0; i < count; i++) {
            shieldedValues[i] = Encode.forHtml(values[i]);
        }
        return shieldedValues;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        String requestBody = getRequestBody(new BufferedReader(new InputStreamReader(super.getInputStream())));
        String shieldedBody = JsonSanitizer.sanitize(requestBody);
        if (shieldedBody != null)
            return new ServletInputStream() {
                private final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                        shieldedBody.getBytes()
                );

                @Override
                public boolean isFinished() {
                    return false;
                }

                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setReadListener(ReadListener readListener) {

                }

                @Override
                public int read() throws IOException {
                    return byteArrayInputStream.read();
                }
            };
        return null;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        String json = getRequestBody(super.getReader());
        String shieldedBody = JsonSanitizer.sanitize(json);
        if (shieldedBody != null)
            return new BufferedReader(new InputStreamReader(new ByteArrayInputStream(shieldedBody.getBytes())));
        return null;
    }

    private static String getRequestBody(BufferedReader br){
        StringBuilder requestBody = new StringBuilder();
        String line;
        try {
            while ((line = br.readLine()) != null)
                requestBody.append(line);
        } catch (IOException e){
            log.warn(e.getMessage());
        }
        return requestBody.toString();
    }
}
