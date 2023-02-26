const dbRoles = [{id: 1, role: "ADMIN", authority: "ADMIN"}, {id: 2, role: "USER", authority: "USER"}]
let idForm = 0
let idFormDEL = 0

const on = (element, event, selector, handler) => {
    element.addEventListener(event, e => {
        if (e.target.closest(selector)) {
            console.log("btnEditClick")
            handler(e)
        }
    })
}

on(document, 'click', '.btnEdit', e => {
    const row = e.target.parentNode.parentNode
    idForm = row.firstElementChild.innerHTML
    $('#editModal').modal("show")
    fetch(url + idForm, {
        method: 'GET'
    })
        .then(response => response.json())
        .then(data => getUserById(data))
        .catch(error => console.log(error))
    const getUserById = (user) => {
        document.getElementById('idEdit').value = user.id
        document.getElementById('firstNameEdit').value = user.firstName
        document.getElementById('lastNameEdit').value = user.lastName
        document.getElementById('ageEdit').value = user.age
        document.getElementById('usernameEdit').value = user.username
        document.getElementById('passwordEdit').value = user.password
        document.getElementById('rolesEdit').size = dbRoles.length;
        let temp = "";
        dbRoles.forEach(r => {
            let select = "";
            user.roles.forEach(rUser => {
                if (rUser.id == r.id) {
                    select = " selected";
                }
            })
            temp += "<option" + select + ">" + r.role + "</option>";
        })
        document.getElementById("rolesEdit").innerHTML = temp;
    }
    document.getElementById('formedit').addEventListener('submit', (e) => {
        e.preventDefault()
        let listRoles = roleArray(document.getElementById('rolesEdit'))

        fetch(url, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: idForm,
                firstName: document.getElementById('firstNameEdit').value,
                lastName: document.getElementById('lastNameEdit').value,
                age: document.getElementById('ageEdit').value,
                username: document.getElementById('usernameEdit').value,
                password: document.getElementById('passwordEdit').value,
                roles: listRoles
            })
        })
            .then(res => res.json())
            .then(data => console.log(data))
            .catch(error => console.log(error))
            .then(reloadShowUsers)
        $('#editModal').modal("hide")
    })
})


on(document, 'click', '.btnDelete', e => {
    const row = e.target.parentNode.parentNode
    idFormDEL = row.firstElementChild.innerHTML
    $('#deleteModal').modal("show")
    fetch(url + idForm, {
        method: 'GET'
    })
        .then(response => response.json())
        .then(data => getUserById(data))
        .catch(error => console.log(error))
    const getUserById = (user) => {
        document.getElementById('idDelete').value = user.id
        document.getElementById('firstNameDelete').value = user.firstName
        document.getElementById('lastNameDelete').value = user.lastName
        document.getElementById('ageDelete').value = user.age
        document.getElementById('usernameDelete').value = user.username
        document.getElementById('rolesDelete').size = dbRoles.length;
        let tempDel = "";
        dbRoles.forEach(r => {
            let select = "";
            user.roles.forEach(rUser => {
                if (rUser.id == r.id) {
                    select = " selected";
                }
            })
            tempDel += "<option" + select + ">" + r.role + "</option>";
        })
        document.getElementById("rolesDelete").innerHTML = tempDel;
    }
    document.getElementById('formdelete').addEventListener('submit', (e) => {
        e.preventDefault()
        fetch(url + idFormDEL, {
            method: 'DELETE'
        })
            .then(data => console.log(data))
            .catch(error => console.log(error))
            .then(reloadShowUsers)
        $('#deleteModal').modal("hide")
    })
})

let roleArray = (options) => {
    let array = []
    for (let i = 0; i < options.length; i++) {
        if (options[i].selected) {
            let role = {id: dbRoles[i].id}
            array.push(role)
        }
    }
    return array
}

const reloadShowUsers = () => {
    fetch(url)
        .then(response => response.json())
        .then(data => {
            result = ''
            show(data)
        })
}
