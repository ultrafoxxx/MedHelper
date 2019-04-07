
window.addEventListener("load", function (ev) {
    var changeButtons = document.getElementsByClassName("myChange");
    for(var i = 0;i < changeButtons.length;i++){
        changeButtons.item(i).addEventListener("click", function (evt) {
            evt.target.parentElement.previousSibling.previousSibling.childNodes[1].removeAttribute("readonly");
        })
    }
});