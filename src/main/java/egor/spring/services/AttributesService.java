package egor.spring.services;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;


public interface AttributesService {

  boolean hasErrors(Model model);

  void addErrors(BindingResult br, Model model);

  void addFormActionAndMethod(String action, String method, Model model);

}
