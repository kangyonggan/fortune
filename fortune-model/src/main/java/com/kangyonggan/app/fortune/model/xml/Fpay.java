package com.kangyonggan.app.fortune.model.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 * @author kangyonggan
 * @since 5/4/17
 */
@XStreamAlias("fpay")
@Data
public class Fpay {

    private Header header;

    private Body body;

}
