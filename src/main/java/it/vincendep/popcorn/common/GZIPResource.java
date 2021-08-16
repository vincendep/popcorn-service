package it.vincendep.popcorn.common;

import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.channels.ReadableByteChannel;
import java.util.zip.GZIPInputStream;

public class GZIPResource implements Resource {

    private final Resource resource;

    public GZIPResource(Resource resource) {
        this.resource = resource;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new GZIPInputStream(resource.getInputStream());
    }

    @Override
    public boolean exists() {
        return resource.exists();
    }

    @Override
    public boolean isReadable() {
        return resource.isReadable();
    }

    @Override
    public boolean isOpen() {
        return resource.isOpen();
    }

    @Override
    public boolean isFile() {
        return resource.isFile();
    }

    @Override
    public URL getURL() throws IOException {
        return resource.getURL();
    }

    @Override
    public URI getURI() throws IOException {
        return resource.getURI();
    }

    @Override
    public File getFile() throws IOException {
        return resource.getFile();
    }

    @Override
    public ReadableByteChannel readableChannel() throws IOException {
        return resource.readableChannel();
    }

    @Override
    public long contentLength() throws IOException {
        return resource.contentLength();
    }

    @Override
    public long lastModified() throws IOException {
        return resource.lastModified();
    }

    @Override
    public Resource createRelative(String relativePath) throws IOException {
        return resource.createRelative(relativePath);
    }

    @Override
    public String getFilename() {
        return resource.getFilename();
    }

    @Override
    public String getDescription() {
        return resource.getDescription();
    }
}
