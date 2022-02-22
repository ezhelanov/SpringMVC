package egor.jsp.tld.egortags.cutpaste;

import javax.servlet.jsp.JspException;

public class CutBodyTag extends AbstractCutPasteTag {

  @Override
  public int doAfterBody() throws JspException {
    pageContext.getServletContext().setAttribute(
      CUT_BODY_ATTRIBUTE, bodyContent.getString()
    );
    return super.doAfterBody();
  }

}
