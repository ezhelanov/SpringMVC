package egor.jsp.tld.egortags.cutpaste;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import java.io.IOException;
import static java.util.Objects.nonNull;

public class PasteBodyTag extends AbstractCutPasteTag {

  @Override
  public int doEndTag() throws JspException {
    ServletContext servletContext = pageContext.getServletContext();
    String cutSnippet = (String) servletContext.getAttribute(CUT_BODY_ATTRIBUTE);
    if (nonNull(cutSnippet)){
      try {
        pageContext.getOut().println(cutSnippet);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return EVAL_PAGE;
  }


}
