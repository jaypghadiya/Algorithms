// C program for implementation of Bubble sort
#include <stdio.h>
void swap(int *xp, int *yp)
{
    int temp = *xp;
    *xp = *yp;
    *yp = temp;
}

// A function to implement optimised bubble sort
void bubbleSort(int arr[], int n)
{
    int i, j, isSorted = 0;
    for (i = 0; isSorted == 0 && i < n - 1; i++)
    {
        isSorted = 1;
        // Last i elements are already in place
        for (j = 0; j < n - i - 1; j++)
        {
            if (arr[j] > arr[j + 1])
            {
                isSorted = 0;
                swap(&arr[j], &arr[j + 1]);
            }
        }
    }
}

/* Function to print an array */
void printArray(int arr[], int size)
{
    int i;
    for (i = 0; i < size; i++)
        printf("%d ", arr[i]);
    printf("\n");
}

// Driver program to test above functions
int main()
{
    int arr[] = {64, 34, -1, 25, 12, 5, 22, 11, 90};
    int n = sizeof(arr) / sizeof(arr[0]);

    printf("Original Array: \n");
    printArray(arr, n);

    bubbleSort(arr, n);
    
    printf("Sorted Array: \n");
    printArray(arr, n);
    
    return 0;
}
