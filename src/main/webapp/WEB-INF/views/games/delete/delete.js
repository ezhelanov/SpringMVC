const dom = document

let btns = dom.querySelectorAll("button")

let form = dom.querySelector("form")
let nohref = dom.querySelector("a")

let spinner = dom.getElementById("spinner")

function disable(button){
    button.disabled = true;
    button.style.cursor = "default";
}

form.onsubmit = () => {
    disable(btns[0])
    disable(btns[1])
    nohref.removeAttribute("href");
    nohref.style.color = "grey";
    spinner.style.visibility = "visible";
}

nohref.onclick = () => disable(btns[0]);
btns[1].onclick = () => disable(btns[0]);
