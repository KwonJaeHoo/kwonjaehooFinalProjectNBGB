const userChangeBtn = document.getElementById("userChangeBtn");
const instructorChangeBtn = document.getElementById("instructorChangeBtn");
const container = document.querySelector(".container");

userChangeBtn.addEventListener("click", () => 
{
	container.classList.remove("right-panel-active");
});

instructorChangeBtn.addEventListener("click", () => 
{
	container.classList.add("right-panel-active");
});
