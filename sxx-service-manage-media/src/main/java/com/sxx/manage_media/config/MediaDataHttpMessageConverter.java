package com.sxx.manage_media.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sxx.framework.domain.media.MediaData;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author hyz
 * @create 2018/12/29 0029
 * @since 1.0.0
 */
public class MediaDataHttpMessageConverter implements HttpMessageConverter<MediaData> {
    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        return MediaData.class == clazz;
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return MediaData.class == clazz;
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        List<MediaType> list = new ArrayList<>();
        list.add(MediaType.MULTIPART_FORM_DATA);
        return list;
    }

    @Override
    public MediaData read(Class<? extends MediaData> clazz, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        InputStream istream = httpInputMessage.getBody();
        String requestString = IOUtils.toString(istream, "UTF-8");

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(requestString, MediaData.class);
    }

    @Override
    public void write(MediaData MediaData, MediaType mediaType, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {

    }
}
