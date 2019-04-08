
$(".myChange").click(function () {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "http://localhost:8080/receptionistPanel/removeVisit?visitId="+$(this).attr("id"),
        cache: false,
        timeout: 600000,
        success: function (data) {
            
        },
        error: function (e) {

            console.log("ERROR");

        }
    });
});
function ajaxDeleteVisit() {

}
