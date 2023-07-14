package cs3500.pa03.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.Socket;
import java.util.List;

/**
 * MockSocket class that is used for testing purposes
 */
public class MockSocket extends Socket {

  private final InputStream testInputs;
  private final ByteArrayOutputStream testLog;

  /**
   * Mock socket constructor that is used for testing
   */
  public MockSocket(ByteArrayOutputStream testLog, List<String> toSend) {
    this.testLog = testLog;

    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);
    for (String message : toSend) {
      printWriter.println(message);
    }
    this.testInputs = new ByteArrayInputStream(stringWriter.toString().getBytes());
  }

  @Override
  public InputStream getInputStream() {
    return this.testInputs;
  }

  @Override
  public OutputStream getOutputStream() {
    return this.testLog;
  }

  @Override
  public boolean isClosed() {
    return false;
  }
}
