package egor.spring.services.impl;

import egor.spring.services.AttributesService;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static java.util.Objects.nonNull;

@Component
public class AttributesServiceImpl implements AttributesService {

  @Override
  public boolean hasErrors(Model model) {
    return nonNull(model.getAttribute("nameError")) || nonNull(model.getAttribute("yearError"));
  }

  @Override
  public void addErrors(BindingResult br, Model model) {
    model.addAttribute("nameError", br.getFieldError("name"));
    model.addAttribute("yearError", br.getFieldError("year"));
  }

  @Override
  public void addFormActionAndMethod(String action, String method, Model model) {
    model.addAttribute("formAction", action);
    model.addAttribute("formMethod", method);
  }

}
