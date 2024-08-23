import java.util.*;
class CourseWork{
	public final static void clearConsole(){
		try{
			final String os=System.getProperty("os.name");
			if(os.contains("Windows")) {
				new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
			}else{
				System.out.print("\033[H\033[2J");
				System.out.flush();
			}
		}catch(final Exception e){
		e.printStackTrace();
		// Handle any exceptions.
		}
	}
	public static void homePage(String[][] students,String[][] studentMarks){
		Scanner input=new Scanner(System.in);
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.println("|                    WELCOME TO GDSE MARKS MANAGEMENT SYSTEM                      |");
		System.out.println("-----------------------------------------------------------------------------------\n");
		System.out.println("[1] Add New Student                      [2]  Add New Student With Marks");
		System.out.println("[3] Add Marks                            [4]  Update Student Details");
		System.out.println("[5] Update Marks                         [6]  Delete Student");
		System.out.println("[7] Print Student Details                [8]  Print Student Ranks");
		System.out.println("[9] Best in Programming Fundamentals     [10] Best in Database Management Systems\n");
		System.out.print("Enter an option to continue > ");
		String option=input.nextLine();
		clearConsole();
		switch(option){
			case "1" : addStudent(students,studentMarks);break;
			case "2" : addStudentWithMarks(students,studentMarks);break;
			case "3" : addMarks(students,studentMarks);break;
			case "4" : updateStudentDetails(students,studentMarks);break;
			case "5" : updateMarks(students,studentMarks);break;
			case "6" : deleteStudent(students,studentMarks);break;
			case "7" : printStudentDetails(students,studentMarks);break;
			case "8" : printStudentRanks(students,studentMarks);break;
			case "9" : bestInPF(students,studentMarks);break;
			case "10": bestInDBMS(students,studentMarks);break;
			case "java AppTerminator" : terminateApp();break;
			default  : homePage(students,studentMarks);break;
		}
	}
	public static boolean studentExists(String[] exist,String e){
		for(int i=0;i<exist.length;i++){
			if(e.equals(exist[i])){
				return true;
			}
		}
		return false;
	}
	public static boolean studentMarksExists(String[] existMarks,String eM){
		for(int i=0;i<existMarks.length;i++){
			if(eM.equals(existMarks[i])){
				return true;
			}
		}
		return false;
	}
	public static int findStudent(String[] x,String ID){
		int index=0;
		for(int i=0;i<x.length;i++){
			if(ID.equals(x[i])){
				index=i;
			}
		}
		return index;
	}
 	public static String[][] sortStudents(String[][] studentMarks){
		String[][] temp=new String[4][studentMarks[0].length];
		for(int i=0;i<temp.length-2;i++){
			for(int j=0;j<temp[i].length;j++){
				temp[i][j]=studentMarks[i][j];
			}
		}
		for(int i=0;i<temp[0].length;i++){
			int total=Integer.valueOf(studentMarks[2][i])+Integer.valueOf(studentMarks[3][i]);
			temp[2][i]=Integer.toString(total);
			double avg=(double)total/2;
			temp[3][i]=String.format("%.2f",avg);
		}
		for(int i=temp[0].length;i>0;i--){ 
			int min=Integer.valueOf(temp[2][0]);
			int index=0;
			for(int j=1;j<i;j++){
				if(Integer.valueOf(temp[2][j])<min){
					min=Integer.valueOf(temp[2][j]);
					index=j;
				}
			}
			String[] x=new String[4];
			for(int j=0;j<x.length;j++){
				x[j]=temp[j][i-1];
				temp[j][i-1]=temp[j][index];
				temp[j][index]=x[j];
			}
		}
		String[][] sortedMarks=new String[5][temp[0].length];
		for(int i=0;i<sortedMarks[0].length;i++){	
			sortedMarks[0][i]=Integer.toString(i+1);
			for(int j=1;j<sortedMarks.length;j++){
				sortedMarks[j][i]=temp[j-1][i];
			}
		}
		for(int i=0;i<sortedMarks[0].length-1;i++){	
			if(sortedMarks[3][i].equals(sortedMarks[3][i+1])){
				sortedMarks[0][i+1]=sortedMarks[0][i];
			}
		}
		return sortedMarks;
	}
	public static String[][] sortStudentsByPF(String[][] studentMarks){
		String[][] sortedPF=new String[4][studentMarks[0].length];
		for(int i=0;i<sortedPF.length;i++){
			for(int j=0;j<sortedPF[i].length;j++){
				sortedPF[i][j]=studentMarks[i][j];
			}
		}
		for(int i=sortedPF[0].length;i>0;i--){ 
			int min=Integer.valueOf(sortedPF[2][0]);
			int index=0;
			for(int j=1;j<i;j++){
				if(Integer.valueOf(sortedPF[2][j])<min){
					min=Integer.valueOf(sortedPF[2][j]);
					index=j;
				}
			}
			String[] temp=new String[4];
			for(int j=0;j<temp.length;j++){
				temp[j]=sortedPF[j][i-1];
				sortedPF[j][i-1]=sortedPF[j][index];
				sortedPF[j][index]=temp[j];
			}
		}
		return sortedPF;
	}
	public static String[][] sortStudentsByDBMS(String[][] studentMarks){
		String[][] sortedDBMS=new String[4][studentMarks[0].length];
		for(int i=0;i<sortedDBMS.length;i++){
			for(int j=0;j<sortedDBMS[i].length;j++){
				sortedDBMS[i][j]=studentMarks[i][j];
			}
		}
		for(int i=sortedDBMS[0].length;i>0;i--){ 
			int min=Integer.valueOf(sortedDBMS[3][0]);
			int index=0;
			for(int j=1;j<i;j++){
				if(Integer.valueOf(sortedDBMS[3][j])<min){
					min=Integer.valueOf(sortedDBMS[3][j]);
					index=j;
				}
			}
			String[] temp=new String[4];
			for(int j=0;j<temp.length;j++){
				temp[j]=sortedDBMS[j][i-1];
				sortedDBMS[j][i-1]=sortedDBMS[j][index];
				sortedDBMS[j][index]=temp[j];
			}
		}
		return sortedDBMS;
	}
	public static String rightAlign(String str,String spaces){
		int length=str.length();
		String aligned=spaces;
		for(int i=0;i<length;i++){
			aligned+="\b";
		}
		aligned+=str;
		return aligned;
	}
	public static String leftAlign(String str,String spaces){
		int length=str.length();
		String aligned=str+spaces;
		for(int i=0;i<length;i++){
			aligned+="\b";
		}
		return aligned;
	}
	public static String getOrdinal(String rank,int count){
		String ordinal="(";
		int r=Integer.valueOf(rank);
		if(r==count && r!=1){
			ordinal+="Last";
		}else{
			if(r>9 && r<13 || r==19){
				switch(r){
					case 10 : ordinal+="Tenth";break;
					case 11 : ordinal+="Eleventh";break;
					case 12 : ordinal+="Twelfth";break;
					case 19 : ordinal+="Nineteenth";break;
				}
			}else{
				int d1=r%10;
				int d2=(r/10)%10;
				String str1="",str2="";
				switch(d1){
					case 1 : str1+="First";break;
					case 2 : str1+="Second";break;
					case 3 : str1+="Third";break;
					case 4 : str1+="Fourth";break;
					case 5 : str1+="Fifth";break;
					case 6 : str1+="Sixth";break;
					case 7 : str1+="Seventh";break;
					case 8 : str1+="Eighth";break;
					case 9 : str1+="Ninth";break;
					case 0 : str1+="\b\bieth";break;
				}
				switch(d2){
					case 1 : str2+="\b\bteenth";break;
					case 2 : str2+="Twenty ";break;
					case 3 : str2+="Thirty ";break;
					case 4 : str2+="Forty ";break;
					case 5 : str2+="Fifty ";break;
					case 6 : str2+="Sixty ";break;
					case 7 : str2+="Seventy ";break;
					case 8 : str2+="Eighty ";break;
					case 9 : str2+="Ninty ";break;
					case 0 : str2+="";break;
				}
				if(r>10 && r<20){
					ordinal+=str1+str2;
				}else{
					ordinal+=str2+str1;
				}
			}
		}
		ordinal+=")";
		return ordinal;
	}
	public static void terminateApp(){
		System.out.print("\n\n\n                              APP TERMINATED!\n\n\n");
	}
	public static void addStudent(String[][] students,String[][] studentMarks){
		Scanner input=new Scanner(System.in);
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.println("|                                  ADD NEW STUDENT                                |");
		System.out.println("-----------------------------------------------------------------------------------");
		int count=students[0].length;
		String[][] temp;
		temp=students;
		System.out.print("\nEnter Student ID   : ");
		String sID=input.nextLine();
		boolean exist=studentExists(students[0],sID);
		while(exist){
			System.out.println("The Student ID already exists");
			System.out.print("\nEnter Student ID   : ");
			sID=input.nextLine();
			exist=studentExists(students[0],sID);
		}
		count++;
		students=new String[2][count];
		System.out.print("Enter Student Name : ");
		String sName=input.nextLine();
		students[0][count-1]=sID;
		students[1][count-1]=sName;
		for(int i=0;i<temp.length;i++){
			for(int j=0;j<temp[i].length;j++){
				students[i][j]=temp[i][j];
			}
		}
		/*for(int i=0;i<students.length;i++){
			for(int j=0;j<students[i].length;j++){
				System.out.print(students[i][j]+"\t");
			}
			System.out.println();
		}*/
		System.out.print("\nStudent has been added successfully. Do you want to add a new student (Y/N)? ");
		while(true){
			String option=input.nextLine();
			switch(option){
				case "Y" :
				case "y" :  clearConsole();
							addStudent(students,studentMarks);break;
				case "N" :
				case "n" :  clearConsole();
							homePage(students,studentMarks);break;
			}
		}
	}
	public static void addStudentWithMarks(String[][] students,String[][] studentMarks){
		Scanner input=new Scanner(System.in);
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.println("|                             ADD NEW STUDENT WITH MARKS                          |");
		System.out.println("-----------------------------------------------------------------------------------");
		int count=students[0].length;
		String[][] temp;
		temp=students;
		System.out.print("\nEnter Student ID   : ");
		String sID=input.nextLine();
		boolean exist=studentExists(students[0],sID);
		while(exist){
			System.out.println("The Student ID already exists");
			System.out.print("\nEnter Student ID   : ");
			sID=input.nextLine();
			exist=studentExists(students[0],sID);
		}
		count++;
		students=new String[2][count];
		System.out.print("Enter Student Name : ");
		String sName=input.nextLine();
		System.out.print("\nProgramming Fundamentals Marks    : ");
		String pfMarks=input.nextLine();
		int pf=Integer.valueOf(pfMarks);
		while(pf>100 || pf<0){
			System.out.println("Invalid marks, please enter correct marks.");
			System.out.print("\nProgramming Fundamentals Marks    : ");
			pfMarks=input.nextLine();
			pf=Integer.valueOf(pfMarks);
		}
		System.out.print("Database Management Systems Marks : ");
		String dbmsMarks=input.nextLine();
		int dbms=Integer.valueOf(dbmsMarks);
		while(dbms>100 || dbms<0){
			System.out.println("Invalid marks, please enter correct marks.");
			System.out.print("\nDatabase Management Systems Marks : ");
			dbmsMarks=input.nextLine();
			dbms=Integer.valueOf(dbmsMarks);
		}
		students[0][count-1]=sID;
		students[1][count-1]=sName;
		for(int i=0;i<temp.length;i++){
			for(int j=0;j<temp[i].length;j++){
				students[i][j]=temp[i][j];
			}
		}
		int countMarks=studentMarks[0].length;
		++countMarks;
		String[][] tempMarks=studentMarks;
		studentMarks=new String[4][countMarks];
		studentMarks[0][countMarks-1]=sID;
		studentMarks[1][countMarks-1]=sName;
		studentMarks[2][countMarks-1]=pfMarks;
		studentMarks[3][countMarks-1]=dbmsMarks;
		for(int i=0;i<tempMarks.length;i++){
			for(int j=0;j<tempMarks[i].length;j++){
				studentMarks[i][j]=tempMarks[i][j];
			}
		}
		/*for(int i=0;i<studentMarks.length;i++){
			for(int j=0;j<studentMarks[i].length;j++){
				System.out.print(studentMarks[i][j]+"\t");
			}
			System.out.println();
		}*/
		tempMarks=studentMarks;
		System.out.print("\nStudent has been added successfully. Do you want to add a new student (Y/N)? ");
		String option=input.nextLine();
		switch(option){
			case "Y" :
			case "y" :  clearConsole();
						addStudentWithMarks(students,studentMarks);break;
			case "N" :
			case "n" :  clearConsole();
						homePage(students,studentMarks);break;
		}
	}
	public static void addMarks(String[][] students,String[][] studentMarks){
		Scanner input=new Scanner(System.in);
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.println("|                                     ADD MARKS                                   |");
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.print("\nEnter Student ID : ");
		String sID=input.nextLine();
		boolean exist=studentExists(students[0],sID);
		boolean existMarks=studentExists(studentMarks[0],sID);
		while(!exist){
			System.out.print("Invalid Student ID. Do you want to search again (Y/N)? ");
			String option=input.nextLine();
			switch(option){
				case "Y" :
				case "y" :  break;
				case "N" :
				case "n" :  clearConsole();
							homePage(students,studentMarks);break;
			}
			System.out.print("\nEnter Student ID : ");
			sID=input.nextLine();
			exist=studentExists(students[0],sID);
			existMarks=studentExists(studentMarks[0],sID);
		}
		if(existMarks){
			int index=findStudent(studentMarks[0],sID);
			System.out.println("Student Name     : "+studentMarks[1][index]);
			System.out.println("This student's marks have been already added.");
			System.out.println("If you want to update the marks, please use [5] Update Marks option.");
			System.out.print("\nDo you want to add marks for another student (Y/N)? ");
			String option=input.nextLine();
			switch(option){
				case "Y" :
				case "y" :  clearConsole();
							addMarks(students,studentMarks);break;
				case "N" :
				case "n" :  clearConsole();
							homePage(students,studentMarks);break;
			}
		}
		int index=findStudent(students[0],sID);
		System.out.println("Student Name     : "+students[1][index]);
		System.out.print("\nProgramming Fundamentals Marks    : ");
		String pfMarks=input.nextLine();
		int pf=Integer.valueOf(pfMarks);
		while(pf>100 || pf<0){
			System.out.println("Invalid marks, please enter correct marks.");
			System.out.print("\nProgramming Fundamentals Marks    : ");
			pfMarks=input.nextLine();
			pf=Integer.valueOf(pfMarks);
		}
		System.out.print("Database Management Systems Marks : ");
		String dbmsMarks=input.nextLine();
		int dbms=Integer.valueOf(dbmsMarks);
		while(dbms>100 || dbms<0){
			System.out.println("Invalid marks, please enter correct marks.");
			System.out.print("\nDatabase Management Systems Marks : ");
			dbmsMarks=input.nextLine();
			dbms=Integer.valueOf(dbmsMarks);
		}
		int countMarks=studentMarks[0].length;
		++countMarks;
		String[][] tempMarks=studentMarks;
		studentMarks=new String[4][countMarks];
		studentMarks[0][countMarks-1]=sID;
		studentMarks[1][countMarks-1]=students[1][index];
		studentMarks[2][countMarks-1]=pfMarks;
		studentMarks[3][countMarks-1]=dbmsMarks;
		for(int i=0;i<tempMarks.length;i++){
			for(int j=0;j<tempMarks[i].length;j++){
				studentMarks[i][j]=tempMarks[i][j];
			}
		}
		/*for(int i=0;i<studentMarks.length;i++){
			for(int j=0;j<studentMarks[i].length;j++){
				System.out.print(studentMarks[i][j]+"\t");
			}
			System.out.println();
		}*/
		tempMarks=studentMarks;
		System.out.print("Marks have been added. Do you want to add marks for another student (Y/N)? ");
		String option=input.nextLine();
		switch(option){
			case "Y" :
			case "y" :  clearConsole();
						addMarks(students,studentMarks);break;
			case "N" :
			case "n" :  clearConsole();
						homePage(students,studentMarks);break;
		}
	}
	public static void updateStudentDetails(String[][] students,String[][] studentMarks){
		Scanner input=new Scanner(System.in);
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.println("|                              UPDATE STUDENT DETAILS                             |");
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.print("\nEnter Student ID : ");
		String sID=input.nextLine();
		boolean exist=studentExists(students[0],sID);
		while(!exist){
			System.out.print("Invalid Student ID. Do you want to search again (Y/N)? ");
			String option=input.nextLine();
			switch(option){
				case "Y" :
				case "y" :  break;
				case "N" :
				case "n" :  clearConsole();
							homePage(students,studentMarks);break;
			}
			System.out.print("\nEnter Student ID : ");
			sID=input.nextLine();
			exist=studentExists(students[0],sID);
		}
		int index=findStudent(students[0],sID);
		System.out.println("Student Name     : "+students[1][index]);
		System.out.print("\nEnter the new student name : ");
		String sName=input.nextLine();
		students[1][index]=sName;
		boolean existMarks=studentExists(studentMarks[0],sID);
		if(existMarks){
			index=findStudent(studentMarks[0],sID);
			studentMarks[1][index]=sName;
		}	
		System.out.println("\nStudent details has been updated successfully.");
		System.out.print("Do you want to update another student detail (Y/N)? ");
		String option=input.nextLine();
		switch(option){
			case "Y" :
			case "y" :  clearConsole();
						updateStudentDetails(students,studentMarks);break;
			case "N" :
			case "n" :  clearConsole();
						homePage(students,studentMarks);break;
		}
	}
	public static void updateMarks(String[][] students,String[][] studentMarks){
		Scanner input=new Scanner(System.in);
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.println("|                                    UPDATE MARKS                                 |");
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.print("\nEnter Student ID : ");
		String sID=input.nextLine();
		boolean exist=studentExists(students[0],sID);
		boolean existMarks=studentExists(studentMarks[0],sID);
		while(!exist){
			System.out.print("Invalid Student ID. Do you want to search again (Y/N)? ");
			String option=input.nextLine();
			switch(option){
				case "Y" :
				case "y" :  break;
				case "N" :
				case "n" :  clearConsole();
							homePage(students,studentMarks);break;
			}
			System.out.print("\nEnter Student ID : ");
			sID=input.nextLine();
			exist=studentExists(students[0],sID);
			existMarks=studentExists(studentMarks[0],sID);
		}
		if(!existMarks){
			int index=findStudent(students[0],sID);
			System.out.println("Student Name     : "+students[1][index]);
			System.out.println("\nThis student's marks yet to be added.");
			System.out.print("Do you want to update the marks of another student (Y/N)? ");
			String option=input.nextLine();
			switch(option){
				case "Y" :
				case "y" :  clearConsole();
							updateMarks(students,studentMarks);break;
				case "N" :
				case "n" :  clearConsole();
							homePage(students,studentMarks);break;
			}
		}
		int index=findStudent(studentMarks[0],sID);
		System.out.println("Student Name     : "+studentMarks[1][index]);
		System.out.println("\nProgramming Fundamentals Marks    : "+studentMarks[2][index]);
		System.out.println("Database Management Systems Marks : "+studentMarks[3][index]);
		System.out.print("\nEnter new Programming Fundamentals Marks    : ");
		String pfMarks=input.nextLine();
		int pf=Integer.valueOf(pfMarks);
		while(pf>100 || pf<0){
			System.out.println("Invalid marks, please enter correct marks.");
			System.out.print("\nProgramming Fundamentals Marks    : ");
			pfMarks=input.nextLine();
			pf=Integer.valueOf(pfMarks);
		}
		System.out.print("Enter new Database Management Systems Marks : ");
		String dbmsMarks=input.nextLine();
		int dbms=Integer.valueOf(dbmsMarks);
		while(dbms>100 || dbms<0){
			System.out.println("Invalid marks, please enter correct marks.");
			System.out.print("\nDatabase Management Systems Marks : ");
			dbmsMarks=input.nextLine();
			dbms=Integer.valueOf(dbmsMarks);
		}
		studentMarks[2][index]=pfMarks;
		studentMarks[3][index]=dbmsMarks;
		System.out.println("\nMarks have been updated successfully.");
		System.out.print("Do you want to update marks for another student (Y/N)? ");
		String option=input.nextLine();
		switch(option){
			case "Y" :
			case "y" :  clearConsole();
						updateMarks(students,studentMarks);break;
			case "N" :
			case "n" :  clearConsole();
						homePage(students,studentMarks);break;
		}
	}
	public static void deleteStudent(String[][] students,String[][] studentMarks){
		Scanner input=new Scanner(System.in);
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.println("|                                   DELETE STUDENT                                |");
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.print("\nEnter Student ID : ");
		String sID=input.nextLine();
		boolean exist=studentExists(students[0],sID);
		while(!exist){
			System.out.print("Invalid Student ID. Do you want to search again (Y/N)? ");
			String option=input.nextLine();
			switch(option){
				case "Y" :
				case "y" :  break;
				case "N" :
				case "n" :  clearConsole();
							homePage(students,studentMarks);break;
			}
			System.out.print("\nEnter Student ID : ");
			sID=input.nextLine();
			exist=studentExists(students[0],sID);
		}
		int index=findStudent(students[0],sID);
		int count=students[0].length;
		count--;
		String[][] temp=students;
		students=new String[2][count];
		for(int i=0;i<temp.length;i++){
			for(int j=0,k=0;j<temp[i].length;j++){
				if(j==index){continue;}
				students[i][k]=temp[i][j];
				k++;
			}
		}
		boolean existMarks=studentExists(studentMarks[0],sID);
		if(existMarks){
			index=findStudent(studentMarks[0],sID);
			int countMarks=studentMarks[0].length;
			countMarks--;
			String[][] tempMarks=studentMarks;
			studentMarks=new String[4][countMarks];
			for(int i=0;i<tempMarks.length;i++){
				for(int j=0,k=0;j<tempMarks[i].length;j++){
					if(j==index){continue;}
					studentMarks[i][k]=tempMarks[i][j];
					k++;
				}
			}
		}
		System.out.println("Student has been deleted successfully.");
		System.out.print("Do you want to delete another student (Y/N)? ");
		String option=input.nextLine();
		switch(option){
			case "Y" :
			case "y" :  clearConsole();
						deleteStudent(students,studentMarks);break;
			case "N" :
			case "n" :  clearConsole();
						homePage(students,studentMarks);break;
		}
	}
	public static void printStudentDetails(String[][] students,String[][] studentMarks){
		Scanner input=new Scanner(System.in);
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.println("|                              PRINTS STUDENT DETAILS                             |");
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.print("\nEnter Student ID : ");
		String sID=input.nextLine();
		boolean exist=studentExists(students[0],sID);
		boolean existMarks=studentExists(studentMarks[0],sID);
		while(!exist){
			System.out.print("Invalid Student ID. Do you want to search again (Y/N)? ");
			String option=input.nextLine();
			switch(option){
				case "Y" :
				case "y" :  break;
				case "N" :
				case "n" :  clearConsole();
							homePage(students,studentMarks);break;
			}
			System.out.print("\nEnter Student ID : ");
			sID=input.nextLine();
			exist=studentExists(students[0],sID);
			existMarks=studentExists(studentMarks[0],sID);
		}
		if(!existMarks){
			int index=findStudent(students[0],sID);
			System.out.println("Student Name     : "+students[1][index]);
			System.out.println("\nMarks yet to be added.");
			System.out.print("\nDo you want to search another student\'s details (Y/N)? ");
			String option=input.nextLine();
			switch(option){
				case "Y" :
				case "y" :  clearConsole();
							printStudentDetails(students,studentMarks);break;
				case "N" :
				case "n" :  clearConsole();
							homePage(students,studentMarks);break;
			}
		}
		int index=findStudent(studentMarks[0],sID);
		System.out.println("Student Name     : "+studentMarks[1][index]);
		String[][] sortedMarks=sortStudents(studentMarks);
		int sIndex=findStudent(sortedMarks[1],sID);
		String ordinal=getOrdinal(sortedMarks[0][sIndex],sortedMarks[0].length);
		System.out.println("+----------------------------------+-----------------------+");
		System.out.println("|Programming Fundamentals Marks    |"+rightAlign(studentMarks[2][index],"                       ")+"|");
		System.out.println("|Database Management Systems Marks |"+rightAlign(studentMarks[3][index],"                       ")+"|");
		System.out.println("|Total Marks                       |"+rightAlign(sortedMarks[3][sIndex],"                       ")+"|");
		System.out.println("|Average Marks                     |"+rightAlign(sortedMarks[4][sIndex],"                       ")+"|");
		System.out.println("|Rank                              |"+rightAlign((sortedMarks[0][sIndex]+ordinal),"                       ")+"|");
		System.out.println("+----------------------------------+-----------------------+");
		System.out.print("\nDo you want to search another student\'s details (Y/N)? ");
		String option=input.nextLine();
		while(true){
			switch(option){
				case "Y" :
				case "y" :  clearConsole();
							printStudentDetails(students,studentMarks);break;
				case "N" :
				case "n" :  clearConsole();
							homePage(students,studentMarks);break;
				
				default  :  clearConsole();
							System.out.println("-----------------------------------------------------------------------------------");
							System.out.println("|                              PRINTS STUDENT DETAILS                             |");
							System.out.println("-----------------------------------------------------------------------------------");
							System.out.println("\nEnter Student ID : "+studentMarks[0][index]);
							System.out.println("Student Name     : "+studentMarks[1][index]);
							System.out.println("+----------------------------------+-----------------------+");
							System.out.println("|Programming Fundamentals Marks    |"+rightAlign(studentMarks[2][index],"                       ")+"|");
							System.out.println("|Database Management Systems Marks |"+rightAlign(studentMarks[3][index],"                       ")+"|");
							System.out.println("|Total Marks                       |"+rightAlign(sortedMarks[3][sIndex],"                       ")+"|");
							System.out.println("|Average Marks                     |"+rightAlign(sortedMarks[4][sIndex],"                       ")+"|");
							System.out.println("|Rank                              |"+rightAlign((sortedMarks[0][sIndex]+ordinal),"                       ")+"|");
							System.out.println("+----------------------------------+-----------------------+");
							System.out.print("\nDo you want to search another student\'s details (Y/N)? ");
							option=input.nextLine();
			}
		}
	}
	public static void printStudentRanks(String[][] students,String[][] studentMarks){
		Scanner input=new Scanner(System.in);
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.println("|                              PRINT STUDENT RANKS                                |");
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.println("\n+-----+-------+-----------------------+-----------+-------------+");
		System.out.println("|Rank |ID     |Name                   |Total Marks|Average Marks|");
		System.out.println("+-----+-------+-----------------------+-----------+-------------+");
		String[][] sortedMarks=sortStudents(studentMarks);
		for(int i=0;i<sortedMarks[0].length;i++){
			System.out.print("|"+leftAlign(sortedMarks[0][i],"     "));
			System.out.print("|"+leftAlign(sortedMarks[1][i],"       "));
			System.out.print("|"+leftAlign(sortedMarks[2][i],"                       "));
			System.out.print("|"+rightAlign(sortedMarks[3][i],"           "));
			System.out.print("|"+rightAlign(sortedMarks[4][i],"             ")+"|\n");
		}
		System.out.println("+-----+-------+-----------------------+-----------+-------------+");	
		System.out.print("\nDo you want to go back to main menu (Y/N)? ");
		String option=input.nextLine();
		switch(option){
			case "Y" :
			case "y" :  clearConsole();
						homePage(students,studentMarks);break;
			default  :  clearConsole();
						printStudentRanks(students,studentMarks);break;
		}
	}
	public static void bestInPF(String[][] students,String[][] studentMarks){
		Scanner input=new Scanner(System.in);
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.println("|                        BEST IN PROGRAMMING FUNDAMENTALS                         |");
		System.out.println("-----------------------------------------------------------------------------------");
		String[][] sortedPF=sortStudentsByPF(studentMarks);
		System.out.println("\n+-----+-------------------------+--------+----------+");
		System.out.println("|ID   |Name                     |PF Marks|DBMS Marks|");
		System.out.println("+-----+-------------------------+--------+----------+");
		for(int i=0;i<sortedPF[0].length;i++){	
			System.out.print("|"+leftAlign(sortedPF[0][i],"     "));
			System.out.print("|"+leftAlign(sortedPF[1][i],"                         "));
			System.out.print("|"+leftAlign(sortedPF[2][i],"        "));
			System.out.print("|"+leftAlign(sortedPF[3][i],"          ")+"|\n");
		}
		System.out.println("+-----+-------------------------+--------+----------+");
		System.out.print("\nDo you want to go back to main menu (Y/N)? ");
		String option=input.nextLine();
		switch(option){
			case "Y" :
			case "y" :  clearConsole();
						homePage(students,studentMarks);break;
			default  :  clearConsole();
						bestInPF(students,studentMarks);break;
		}
	}
	public static void bestInDBMS(String[][] students,String[][] studentMarks){
		Scanner input=new Scanner(System.in);
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.println("|                       BEST IN DATABASE MAMAGEMENT SYSTEMS                       |");
		System.out.println("-----------------------------------------------------------------------------------");
		String[][] sortedDBMS=sortStudentsByDBMS(studentMarks);
		System.out.println("\n+-----+-------------------------+----------+--------+");
		System.out.println("|ID   |Name                     |DBMS Marks|PF Marks|");
		System.out.println("+-----+-------------------------+----------+--------+");
		for(int i=0;i<sortedDBMS[0].length;i++){	
			System.out.print("|"+leftAlign(sortedDBMS[0][i],"     "));
			System.out.print("|"+leftAlign(sortedDBMS[1][i],"                         "));
			System.out.print("|"+leftAlign(sortedDBMS[3][i],"          "));
			System.out.print("|"+leftAlign(sortedDBMS[2][i],"        ")+"|\n");
		}
		System.out.println("+-----+-------------------------+----------+--------+");
		System.out.print("\nDo you want to go back to main menu (Y/N)? ");
		String option=input.nextLine();
		switch(option){
			case "Y" :
			case "y" :  clearConsole();
						homePage(students,studentMarks);break;
			default  :  clearConsole();
						bestInDBMS(students,studentMarks);break;
		}
	}
	public static void main(String[] args){
		Scanner input=new Scanner(System.in);
		String[][] students=new String[2][0];
		String[][] studentMarks=new String[4][0];
		String initial="";
		do{
			System.out.print("> ");
			initial=input.nextLine();
			clearConsole();
			switch(initial){
				case "java AppInitializer" : homePage(students,studentMarks);break;
				case "java AppTerminator"  : terminateApp();break;
			}
		}while(!initial.equals("java AppTerminator"));	
	}
}
