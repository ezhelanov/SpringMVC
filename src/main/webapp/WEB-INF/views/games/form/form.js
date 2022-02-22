let dom = document;

dom.querySelector("form").onsubmit = () => {
    dom.querySelector("button").disabled = true;
}
