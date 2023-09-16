#include <iostream>
#include <conio.h>
#include <stdlib.h>

using namespace std;

#define size 5

int main()
{
    int arr[size],R=-1,F=0,te=0,ch,n,i,x;

    for(;;)		// An infinite loop
    {
        system("cls");		// for clearing the screen
        cout<<"F="<<F<<"  R="<<R<<endl<<endl;
        cout<<"1. Add Rear\n";
        cout<<"2. Delete Rear\n";
        cout<<"3. Add Front\n";
        cout<<"4. Delete Front\n";
        cout<<"5. Display\n";
        cout<<"6. Exit\n";
        cout<<"Enter Choice: ";
        cin>>ch;

        switch(ch)
        {
            case 1:
                if(te==size)
                {
                    cout<<"Queue is full";
                    getch();	// pause the loop to see the message
                }
                else
                {
                    cout<<"Enter a number ";
                    cin>>n;
                    R=(R+1)%size;
                    arr[R]=n;
                    te=te+1;
                }
                break;

            case 2:
                if(te==0)
                {
                    cout<<"Queue is empty";
                    getch();	// pause the loop to see the message
                }
                else
                {
                    if(R==-1)
                    {
                        R=size-1;
                    }
                    cout<<"Number Deleted From Rear End = "<<arr[R];
                    R=R-1;
                    te=te-1;
                    getch();	// pause the loop to see the number
                }
                break;

            case 3:
                if(te==size)
                {
                    cout<<"Queue is full";
                    getch();	// pause the loop to see the message
                }
                else
                {
                    cout<<"Enter a number ";
                    cin>>n;
                    if(F==0)
                    {
                        F=size-1;
                    }
                    else
                    {
                        F=F-1;
                    }
                    arr[F]=n;
                    te=te+1;
                }
                break;

            case 4:
                if(te==0)
                {
                    cout<<"Queue is empty";
                    getch();	// pause the loop to see the message
                }
                else
                {
                    cout<<"Number Deleted From Front End = "<<arr[F];
                    F=(F+1)%size;
                    te=te-1;
                    getch();	// pause the loop to see the number
                }
                break;

            case 5:
                if(te==0)
                {
                    cout<<"Queue is empty";
                    getch();	// pause the loop to see the message
                }
                else
                {
                    x=F;
                    for(i=1; i<=te; i++)
                    {
                        cout<<arr[x]<<" ";
                        x=(x+1)%size;
                    }
                    getch();	// pause the loop to see the numbers
                }
                break;

            case 6:
                exit(0);
                break;

            default:
                cout<<"Wrong Choice";
                getch();	// pause the loop to see the message
        }
    }
    return 0;
}