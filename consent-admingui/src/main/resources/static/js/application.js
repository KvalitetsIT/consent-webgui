$( document ).ready(function() {
    function stripe() {
       $('tbody tr:visible').each(function(idx) {
           $(this).css("background-color", "inherit");
           if ( idx % 2 == 0) {
             $(this).css("background-color", "#f9f9f9");
           }
       })
    }

    function filter(municipality) {
        $("tbody tr").each(function(idx) {
           value = $(this).find('td').eq(3).text();
           if (municipality != 'Alle' && municipality != value) {
              $(this).hide();
           } else {
              $(this).show();
           }
        })
    }

    $("#kommune").change(function() {
         municipality = this.value;
         filter(municipality);
         stripe();
    })
});