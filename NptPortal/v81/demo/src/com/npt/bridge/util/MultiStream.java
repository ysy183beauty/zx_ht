package com.npt.bridge.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 项目: NPTWebApp
 * 作者: 张磊
 * 日期: 16/11/28 下午4:9
 * 备注: 多输出流
 */
public class MultiStream extends OutputStream {
    private List<OutputStream> streamList = new ArrayList<OutputStream>();

    public MultiStream(OutputStream... outputStreams) {
        streamList.addAll(Arrays.asList(outputStreams));
    }

    @Override
    public void write(int b) throws IOException {

    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        IOException e = null;
        for (OutputStream o : streamList) {
            try {
                o.write(b, off, len);
            } catch (IOException ioE) {
                e = ioE;
            }
        }

        if (e != null) {
            throw e;
        }
    }

    @Override
    public void close() throws IOException {
        IOException e = null;
        //依次调用多个输出流的close方法
        for (OutputStream o : streamList) {
            try {
                o.close();
            } catch (IOException ioE) {
                e = ioE;
            }
        }

        if (e != null) {
            throw e;
        }
    }
}
