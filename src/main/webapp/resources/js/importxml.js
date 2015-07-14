$(document).ready(function() {

	function validate() {
		var value = form.file.value;
		ext = value.split(".").pop();
		if (!value) {
			alert("Please select a file.");
		} else if (ext !== "xml") {
			alert("Please select a .xml file.");
		}
		return (ext === "xml");
	}
});
