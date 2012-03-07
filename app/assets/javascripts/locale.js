$(document).on("change", "form select#locale", function () {
	$(this).parent("form").submit();
});
