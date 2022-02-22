package egor.spring.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class MyRestController {

  @Autowired private ExceptionService exceptionService;

  @RequestMapping(
    value = "/get",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<Map<String, Object>> get(
    @RequestParam(name = "param", required = false, defaultValue = "0") int param
  ) throws Exception {

    if (param == 5) throw new RuntimeException();
    if (param == 10) throw new IllegalAccessException();
    if (param == 25) throw new ClassNotFoundException();

    Map<String, Object> messages = exceptionService.getMapOfMessages();
    messages.put("message", "Всё нормас");
    return ResponseEntity
      .ok(messages);
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<Map<String, Object>> ex(Exception e){
    Map<String, Object> messages = exceptionService.getMapOfMessages();
    messages.put("message", e.getClass().getSimpleName());
    return ResponseEntity
      .status(HttpStatus.INTERNAL_SERVER_ERROR)
      .body(messages);
  }

  @ExceptionHandler({ IllegalAccessException.class, IndexOutOfBoundsException.class })
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public Map<String, Object> ex(){
    Map<String, Object> messages = exceptionService.getMapOfMessages();
    messages.put("message", "IllegalAccessException");
    return messages;
  }

  @ExceptionHandler(ClassNotFoundException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity ex_two(){
    return ResponseEntity
      .status(HttpStatus.OK)
      .build();
  }


}
