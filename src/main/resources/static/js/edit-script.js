loadProfileData();

async function loadProfileData() {
    let response = await fetch("/api/v1/profile/", {
        method: 'GET'
    });
    if (response.ok) {
        let profile = await response.json();
        printProfileData(profile);
    } else {

    }
}

function printProfileData(profile) {
    printUsername(profile);
    printImage(profile);
    printRole(profile);
    printProfileDetails(profile);
}

function printImage(profile) {
    let image = document.querySelector(".profile-picture");
    image.src = profile.userPic;
    image.style.opacity = "1";
}

function printUsername(profile) {
    let username = document.querySelector(".username");
    username.textContent = profile.login;
    username.style.display = "block";
}



function printRole(profile) {
    const role = document.querySelector(".diagonal-text");
    role.textContent = profile.role;
}

function printProfileDetails(profile) {
    const fullName = document.querySelector("#fullName");
    fullName.value = profile.name + " " + profile.surname;

    let nickName = document.querySelector("#nickname");
    nickName.value = profile.nickname;
}

const form = document.querySelector("#form");
form.addEventListener("submit",  async function (event) {
    event.preventDefault();

    let formData = new FormData(form);

    const fullName = formData.get('fullName');
    let strs= fullName.split(" ");
    formData.set('name',strs[0]);
    formData.set('surname',strs[1]);

    let response = await fetch("/api/v1/profile/edit", {
        method: 'PUT',
        body: formData
    })

    if (response.ok) {
        let newData = await response.json();
        printProfileData(newData);
    }
    else {

    }
})

