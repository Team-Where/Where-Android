# Where-and

## Compose 혼용 사용법

### Compose에서 Fragment 사용하기
~~~kotlin
@Composable
fun DrawerContent() {
    //TODO Add FragmentBinding
    AndroidViewBinding(FragmentSignInBinding::inflate) {
        editTextEmail.setText("이런식으로 수정해요")
    }
}
~~~~

### Compose에서 View 사용하기
~~~kotlin
@Composable
fun CustomView() {
    var selectedItem by remember { mutableStateOf(0) }

    // Adds view to Compose
    AndroidView(
        modifier = Modifier.fillMaxSize(), // Occupy the max size in the Compose UI tree
        factory = { context ->
            // Creates view
            MyView(context).apply {
                // Sets up listeners for View -> Compose communication
                setOnClickListener {
                    selectedItem = 1
                }
            }
        },
        update = { view ->
            // View's been inflated or state read in this block has been updated
            // Add logic here if necessary

            // As selectedItem is read here, AndroidView will recompose
            // whenever the state changes
            // Example of Compose -> View communication
            view.selectedItem = selectedItem
        }
    )
}
~~~~