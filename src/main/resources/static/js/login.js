const userChangeBtn = document.getElementById("userChangeBtn");
const instructorChangeBtn = document.getElementById("instructorChangeBtn");
const userForm = document.getElementById("userForm");
const instructorForm = document.getElementById("instructorForm");
const container = document.querySelector(".container");

const loginProvider = document.getElementById('loginProvider');

userChangeBtn.addEventListener("click", () => 
{
	container.classList.remove("right-panel-active");
	loginProvider.value = "user"
	
});

instructorChangeBtn.addEventListener("click", () => 
{
	container.classList.add("right-panel-active");
	loginProvider.value = "instructor"
});

userForm.addEventListener("submit", (e) => e.preventDefault());
instructorForm.addEventListener("submit", (e) => e.preventDefault());