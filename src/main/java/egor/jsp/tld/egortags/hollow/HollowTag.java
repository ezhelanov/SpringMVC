package egor.jsp.tld.egortags.hollow;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class HollowTag extends TagSupport {

  @Override
  public int doStartTag() throws JspException {
    try {
      pageContext.getOut().println("<var>Hollow tag</var>");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return SKIP_BODY;
  }

}
