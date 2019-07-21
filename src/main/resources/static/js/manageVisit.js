$("#look").on("click", function () {
    $("#zwolnienie").show();
});

$("#reciept").on("click", function () {
    var hasEnded = $("#hasEnded");
    hasEnded.val(true);
    $("#myForm").submit();
});
