
var searchButton = $("#submitButton");

var foundDrugs = $("#tableBody");

var selectedDrugs = $("#selectedDrugs")

var inputDrug = $("#inputDate");

var prescribedList = [];

var precribeButton = $("#submitPrescription");



searchButton.click(function (event) {
    ajaxgetDrugs(inputDrug.val());
});

precribeButton.on("click", function () {
    ajaxsavePrescription();
});


function ajaxgetDrugs(drugName) {


    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "http://localhost:8080/doctorPanel/getDrugData",
        cache: false,
        timeout: 600000,
        data: drugName,
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        success: function (data) {
            foundDrugs.empty();
            $.each(data, function (index, value) {
                var row = $("<tr>");
                var drugName = $("<td>");
                drugName.text(value.nazwa);
                row.append(drugName);
                var drugForm = $("<td>");
                drugForm.text(value.forma);
                row.append(drugForm);
                var dosage = $("<td>");
                dosage.text(value.dawka);
                row.append(dosage);
                var addButton = $("<td>");
                var hiddenInput = $("<input type='hidden' class='addInput' name='addInput'>");
                hiddenInput.attr('value', value.id);
                addButton.append(hiddenInput);
                var reserveButton = $('<button type="button" class="btn btn-success reserveButton">');
                reserveButton.text('Dodaj');
                addButton.append(reserveButton);
                row.append(addButton);
                foundDrugs.append(row);
            });
            $(".reserveButton").on("click", function () {
                prescribedList.push(parseInt($(this).prev().val()));
                var toAdd = $(this).parent().parent().clone();
                toAdd.children(3).children(1).text("Usuń");
                toAdd.children(3).children(1).removeClass("reserveButton");

                toAdd.children(3).children(1).on("click", function () {
                    var newList = [];
                    var hasCut = false;
                    for(var i = 0;i<prescribedList.length;i++){
                        if(prescribedList[i] === parseInt($(this).prev().val()) && !hasCut){
                            hasCut = true;
                        }
                        else {
                            newList.push(prescribedList[i]);
                        }
                    }
                    prescribedList = newList;
                    $(this).parent().parent().remove();
                    console.log(prescribedList);
                });
                console.log(prescribedList);
                $("#tableBodySelected").append(toAdd);
            })
        }
    })
}


function ajaxsavePrescription() {
    console.log(prescribedList);


    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "http://localhost:8080/doctorPanel/submitPrescription",
        cache: false,
        timeout: 600000,
        data: JSON.stringify(prescribedList),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        success: function (data) {
            window.location.replace("http://localhost:8080/doctorPanel");
        }
    })
}
