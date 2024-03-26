
    // Get the button element
    //
    // var createAccountBtn = document.getElementById("createAccountBtn");
    //
    // // Add click event listener to the button
    // createAccountBtn.addEventListener("click", function() {
    // // Open the pop-up window
    // window.open("create-account.html", "Create Account", "width=500,height=500"); });


    document.getElementById('budgetForm').addEventListener('submit',
        function(event) {
        event.preventDefault();
        const budgetAmount = document.getElementById('budgetAmount').value;
        fetch('/setBudget', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ budgetAmount: budgetAmount })
        })
            .then(response => {
                if (response.ok) {
                    console.log('Budget set successfully');
                    // Show the budget in the HTML
                    document.getElementById('budgetDisplay').innerText = "Budget: $" + budgetAmount;
                } else {
                    console.error('Failed to set budget');
                    // You can add further actions here, like showing an error message
                }
            })
            .catch(error => {
                console.error('Error:', error);
                // You can add further actions here, like showing an error message
            });
    });
