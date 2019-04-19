window.addEventListener("load", function (ev) {

    var fileInput = document.getElementById("customFile");
    var fileLabel = document.getElementById("label");

    fileInput.addEventListener("change", function (ev1) {
        fileLabel.textContent = "Wybrano plik: "+ fileInput.files[0].name;
    })

});
