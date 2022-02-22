package egor.jsp.tld.egortags.hollow;

import egor.core.entities.GameType;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class HollowWithAttributeTag extends TagSupport {

  private GameType[] types;

  public GameType[] getTypes() {
    return types;
  }

  public void setTypes(GameType[] types) {
    this.types = types;
  }

  @Override
  public int doStartTag() throws JspException {
    for (GameType type : types) {
      try {
        pageContext.getOut().println("<code>" + type + "</code>");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return SKIP_BODY;
  }
}
