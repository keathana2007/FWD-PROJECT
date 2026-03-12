import java.util.*;

// USER CLASS
class User {
    String name;
    String role;
    String email;

    User(String name,String role,String email){
        this.name=name;
        this.role=role;
        this.email=email;
    }
}

// JOB CLASS
class Job {
    int jobId;
    String title;
    String company;
    List<String> skills;

    Job(int jobId,String title,String company,List<String> skills){
        this.jobId=jobId;
        this.title=title;
        this.company=company;
        this.skills=skills;
    }
}

// PROFILE CLASS
class Profile {
    String name;
    List<String> skills;
    String education;
    int experience;

    Profile(String name,List<String> skills,String education,int experience){
        this.name=name;
        this.skills=skills;
        this.education=education;
        this.experience=experience;
    }
}

// LINKED LIST NODE (CO2 ADT IMPLEMENTATION)
class JobNode{
    Job job;
    JobNode next;

    JobNode(Job job){
        this.job=job;
        this.next=null;
    }
}

// JOB LINKED LIST
class JobLinkedList{
    JobNode head;

    void insert(Job job){
        JobNode newNode=new JobNode(job);

        if(head==null){
            head=newNode;
            return;
        }

        JobNode temp=head;

        while(temp.next!=null){
            temp=temp.next;
        }

        temp.next=newNode;
    }

    void display(){
        JobNode temp=head;

        while(temp!=null){
            System.out.println("JobID:"+temp.job.jobId+
                    " Title:"+temp.job.title+
                    " Company:"+temp.job.company+
                    " Skills:"+temp.job.skills);

            temp=temp.next;
        }
    }
}

// MAIN PORTAL
public class SmartJobPortal {

    static Scanner sc=new Scanner(System.in);

    // HASHMAP FOR USERS (CO4 HASHING)
    static HashMap<String,User> users=new HashMap<>();

    // HASHMAP FOR PROFILES
    static HashMap<String,Profile> profiles=new HashMap<>();

    // LINKED LIST FOR JOBS
    static JobLinkedList jobList=new JobLinkedList();

    // PRIORITY QUEUE FOR CANDIDATE SHORTLIST (CO3)
    static PriorityQueue<String> shortlisted=
            new PriorityQueue<>();


    public static void main(String[] args) {

        while(true){

            System.out.println("\n===== SMART JOB PORTAL =====");
            System.out.println("1.Register");
            System.out.println("2.Job Seeker Module");
            System.out.println("3.Recruiter Module");
            System.out.println("4.Admin Module");
            System.out.println("5.Exit");

            int ch=sc.nextInt();

            switch(ch){

                case 1:
                    register();
                    break;

                case 2:
                    jobSeekerModule();
                    break;

                case 3:
                    recruiterModule();
                    break;

                case 4:
                    adminModule();
                    break;

                case 5:
                    System.exit(0);

            }

        }

    }

    // USER REGISTRATION
    static void register(){

        sc.nextLine();

        System.out.print("Enter Name:");
        String name=sc.nextLine();

        System.out.print("Enter Email:");
        String email=sc.nextLine();

        System.out.print("Enter Role(JobSeeker/Recruiter/Admin):");
        String role=sc.nextLine();

        users.put(email,new User(name,role,email));

        System.out.println("Registration Successful");

    }

    // JOB SEEKER MODULE
    static void jobSeekerModule(){

        System.out.println("\n1.Create Profile");
        System.out.println("2.Search Jobs");
        System.out.println("3.Opportunity Analysis");

        int ch=sc.nextInt();

        switch(ch){

            case 1:
                createProfile();
                break;

            case 2:
                jobList.display();
                break;

            case 3:
                analyzeOpportunity();
                break;

        }

    }

    // PROFILE CREATION
    static void createProfile(){

        sc.nextLine();

        System.out.print("Enter Name:");
        String name=sc.nextLine();

        System.out.print("Enter Skills(comma separated):");
        String s=sc.nextLine();

        List<String> skills=Arrays.asList(s.split(","));

        System.out.print("Education:");
        String edu=sc.nextLine();

        System.out.print("Experience:");
        int exp=sc.nextInt();

        profiles.put(name,new Profile(name,skills,edu,exp));

        System.out.println("Profile Created");

    }

    // RECRUITER MODULE
    static void recruiterModule(){

        sc.nextLine();

        System.out.print("Job ID:");
        int id=sc.nextInt();

        sc.nextLine();

        System.out.print("Job Title:");
        String title=sc.nextLine();

        System.out.print("Company:");
        String company=sc.nextLine();

        System.out.print("Required Skills:");
        String s=sc.nextLine();

        List<String> skills=Arrays.asList(s.split(","));

        Job job=new Job(id,title,company,skills);

        jobList.insert(job);

        System.out.println("Job Posted");

    }

    // SKILL MATCH ANALYSIS
    static void analyzeOpportunity(){

        sc.nextLine();

        System.out.print("Enter Your Name:");
        String name=sc.nextLine();

        Profile p=profiles.get(name);

        if(p==null){
            System.out.println("Profile not found");
            return;
        }

        JobNode temp=jobList.head;

        while(temp!=null){

            int match=0;

            for(String skill:p.skills){

                if(temp.job.skills.contains(skill)){
                    match++;
                }

            }

            int percent=(match*100)/temp.job.skills.size();

            System.out.println("\nJob:"+temp.job.title);
            System.out.println("Match:"+percent+"%");

            if(percent>=70)
                System.out.println("Chance: HIGH");
            else if(percent>=40)
                System.out.println("Chance: MEDIUM");
            else
                System.out.println("Chance: LOW");

            temp=temp.next;
        }

    }

    // ADMIN MODULE
    static void adminModule(){

        System.out.println("\n1.View Users");
        System.out.println("2.View Jobs");
        System.out.println("3.Shortlisted Candidates");

        int ch=sc.nextInt();

        switch(ch){

            case 1:

                for(User u:users.values()){
                    System.out.println(u.name+" "+u.role+" "+u.email);
                }

                break;

            case 2:
                jobList.display();
                break;

            case 3:

                while(!shortlisted.isEmpty()){
                    System.out.println(shortlisted.poll());
                }

                break;

        }

    }

}