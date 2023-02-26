window.onload = function () {
    const url = "http://localhost:8080/api/users"
    const formNew = document.getElementById('formnew')
    const firstName = document.getElementById('firstName')
    const lastName = document.getElementById('lastName')
    const age = document.getElementById('age')
    const username = document.getElementById('username')
    const password = document.getElementById('password')

    formNew.addEventListener('submit', (e) => {
        e.preventDefault()
        let listRoles = roleArray(document.getElementById('roles'))
        console.log(
            firstName.value, lastName.value, age.value, username.value, password.value, listRoles
        )
        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                firstName: firstName.value,
                lastName: lastName.value,
                age: age.value,
                username: username.value,
                password: password.value,
                roles: listRoles
            })
        })
            .then(response => response.json())
            .then(data => console.log(data))
            .catch(error => console.log(error))
            .then(reloadShowUsers)
        $('.nav-tabs a[href="#userstable"]').tab('show')
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
}