<script>
    function updateTimers() {
        var date = new Date();
        var hours = date.getHours();
        var minutes = date.getMinutes();
        if (minutes === 0) {
            $("#hours").html(2 - hours % 2);
            $("#minutes").html(0);
        } else {
            $("#hours").html(1 - hours % 2);
            $("#minutes").html(60 - minutes);
        }
    }
    updateTimers();
    window.setInterval(updateTimers, 60 * 1000);
</script>