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
    printNickname(profile);
    printImage(profile);
    printRole(profile);
    printProfileDetails(profile);
}

function printImage(profile) {
    let image = document.querySelector(".profile-picture");
    image.src = profile.userPic;
    image.style.opacity = "1";
}
function printImageUrl(src) {
    let image = document.querySelector(".profile-picture");
    image.src = window.URL.createObjectURL(src);
}

function printNickname(profile) {
    let nickname = document.querySelector(".nickname");
    nickname.textContent = profile.nickname;
    nickname.style.display = "block";
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

const input = document.querySelector("#input__file");
input.addEventListener("change",updateImage);
function updateImage(){
    let files = input.files;
    if (files.length !== 0){
        for (let i = 0; i < files.length; i++) {
            printImageUrl(files[i]);
        }
    }
}