
import java.util.*;

// ==================== USER CLASS ====================
class User {
    String username, password, role;
    
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}

// ==================== USER REGISTRY (HashMap) ====================
class UserRegistry {
    HashMap<String, User> users = new HashMap<>();
    
    public void register(String username, String password, String role) {
        users.put(username, new User(username, password, role));
    }
    
    public boolean login(String username, String password) {
        User u = users.get(username);
        return u != null && u.password.equals(password);
    }
}

// ==================== JOB SEEKER ====================
class JobSeeker extends User {
    List<String> skills = new ArrayList<>();
    String education, experience;
    
    public JobSeeker(String username, String pw, List<String> skills, String ed, String exp) {
        super(username, pw, "JobSeeker");
        this.skills = skills;
        this.education = ed;
        this.experience = exp;
    }
    
    public void addSkill(String skill) {
        skills.add(skill);
    }
    
    public void showProfile() {
        System.out.println("  Username: " + username);
        System.out.println("  Skills: " + skills);
        System.out.println("  Education: " + education);
        System.out.println("  Experience: " + experience);
    }
}

// ==================== JOB CLASS ====================
class Job {
    String title;
    List<String> requiredSkills;
    
    public Job(String title, List<String> requiredSkills) {
        this.title = title;
        this.requiredSkills = requiredSkills;
    }
}

// ==================== JOB PORTAL (ArrayList + Linear Search) ====================
class JobPortal {
    List<Job> jobs = new ArrayList<>();
    
    public void addJob(Job job) {
        jobs.add(job);
    }
    
    // Linear Search - O(n*m) complexity
    public List<Job> searchJobs(String skill) {
        List<Job> result = new ArrayList<>();
        for (Job job : jobs) {
            if (job.requiredSkills.contains(skill)) {
                result.add(job);
            }
        }
        return result;
    }
    
    public List<Job> getAllJobs() {
        return jobs;
    }
}

// ==================== SKILL ANALYZER (Opportunity Analysis Logic) ====================
class SkillAnalyzer {
    
    // Calculate matching percentage - O(m) where m = jobSkills.size()
    public static double calculateMatch(List<String> seekerSkills, List<String> jobSkills) {
        if (jobSkills.size() == 0) return 0;
        int match = 0;
        for (String skill : jobSkills) {
            if (seekerSkills.contains(skill)) {
                match++;
            }
        }
        return (double)match / jobSkills.size() * 100;
    }
    
    // Opportunity Analysis - High/Medium/Low Chance
    public static void analyze(String candidateName, List<String> seekerSkills, String jobTitle, List<String> jobSkills) {
        double percent = calculateMatch(seekerSkills, jobSkills);
        String chance;
        String emoji;
        
        if (percent > 70) {
            chance = "HIGH CHANCE ✓✓✓";
            emoji = "🟢";
        } else if (percent >= 40) {
            chance = "MEDIUM CHANCE ✓✓";
            emoji = "🟡";
        } else {
            chance = "LOW CHANCE ✓";
            emoji = "🔴";
        }
        
        System.out.println("  " + emoji + " Candidate: " + candidateName + " | Job: " + jobTitle);
        System.out.println("    Skill Match: " + String.format("%.2f", percent) + "% → " + chance);
        
        System.out.print("    Skills Matched: ");
        boolean any = false;
        for (String skill : jobSkills) {
            if (seekerSkills.contains(skill)) {
                System.out.print(skill + " ");
                any = true;
            }
        }
        if (!any) System.out.print("None");
        System.out.println();
        
        System.out.print("    Skill Gaps: ");
        any = false;
        for (String skill : jobSkills) {
            if (!seekerSkills.contains(skill)) {
                System.out.print(skill + " ");
                any = true;
            }
        }
        if (!any) System.out.print("None");
        System.out.println();
    }
}

// ==================== RECRUITER ====================
class Recruiter extends User {
    
    public Recruiter(String username, String pw) {
        super(username, pw, "Recruiter");
    }
    
    public void postJob(JobPortal portal, String title, List<String> requiredSkills) {
        portal.addJob(new Job(title, requiredSkills));
        System.out.println("  ✓ Job posted: " + title + " (Required: " + requiredSkills + ")");
    }
    
    // Candidate Shortlisting - Sorting by match percentage O(n log n)
    public List<JobSeeker> shortlistCandidates(List<JobSeeker> seekers, List<String> jobSkills) {
        seekers.sort((a, b) -> Double.compare(
                SkillAnalyzer.calculateMatch(b.skills, jobSkills),
                SkillAnalyzer.calculateMatch(a.skills, jobSkills)
        ));
        return seekers;
    }
}

// ==================== ADMIN ====================
class Admin extends User {
    
    public Admin(String username, String pw) {
        super(username, pw, "Admin");
    }
    
    public void manageUsers(UserRegistry reg) {
        System.out.println("  Registered Users: " + reg.users.keySet());
    }
    
    public boolean verifyJob(Job job) {
        return job != null && job.requiredSkills.size() > 0;
    }
}

// ==================== LINKED LIST (ADT - Custom Implementation) ====================
class LinkedList {
    class Node {
        String data;
        Node next;
        
        Node(String d) {
            data = d;
            next = null;
        }
    }
    
    Node head;
    
    public void insert(String d) {
        Node n = new Node(d);
        n.next = head;
        head = n;
    }
    
    public void traverse() {
        Node cur = head;
        System.out.print("  ");
        while (cur != null) {
            System.out.print(cur.data + " → ");
            cur = cur.next;
        }
        System.out.println("null");
    }
}

// ==================== MAIN CLASS ====================
public class JobPortalMain {
    
    public static void main(String[] args) {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("    🎯 SMART JOB PORTAL - DSA PROJECT (All in One) 🎯");
        System.out.println("=".repeat(70) + "\n");
        
        // ============ 1️⃣ USER REGISTRATION MODULE ============
        System.out.println("1️⃣  USER REGISTRATION MODULE (HashMap - O(1) lookup)");
        System.out.println("-".repeat(70));
        
        UserRegistry registry = new UserRegistry();
        registry.register("admin1", "adminpw", "Admin");
        registry.register("rec1", "recpw", "Recruiter");
        registry.register("alice", "pw1", "JobSeeker");
        registry.register("bob", "pw2", "JobSeeker");
        registry.register("charlie", "pw3", "JobSeeker");
        
        System.out.println("✓ Users registered successfully");
        System.out.println("  Total Users: " + registry.users.size());
        System.out.println("  Login Test - alice: " + (registry.login("alice", "pw1") ? "Success ✓" : "Failed ✗") + "\n");
        
        // ============ 2️⃣ JOB SEEKER MODULE ============
        System.out.println("2️⃣  JOB SEEKER MODULE (Profile Creation & Management)");
        System.out.println("-".repeat(70));
        
        JobSeeker js1 = new JobSeeker("alice", "pw1", Arrays.asList("Java", "DSA", "SQL", "Spring"), "BTech CSE", "2 Years");
        JobSeeker js2 = new JobSeeker("bob", "pw2", Arrays.asList("Java", "Spring", "Docker"), "BTech CSE", "1.5 Years");
        JobSeeker js3 = new JobSeeker("charlie", "pw3", Arrays.asList("Python", "SQL", "Tableau"), "BSc IT", "1 Year");
        
        System.out.println("\nProfile: Alice");
        js1.showProfile();
        
        System.out.println("\nProfile: Bob");
        js2.showProfile();
        
        System.out.println("\nProfile: Charlie");
        js3.showProfile();
        
        System.out.println("\nAdding new skill to alice...");
        js1.addSkill("Kubernetes");
        System.out.println("✓ New skill added. Updated skills: " + js1.skills + "\n");
        
        // ============ 3️⃣ RECRUITER MODULE - JOB POSTING ============
        System.out.println("3️⃣  RECRUITER MODULE - JOB POSTING");
        System.out.println("-".repeat(70));
        
        JobPortal portal = new JobPortal();
        Recruiter rec = new Recruiter("rec1", "recpw");
        
        rec.postJob(portal, "Senior Software Engineer", Arrays.asList("Java", "DSA", "Spring", "SQL", "Kubernetes"));
        rec.postJob(portal, "Junior Developer", Arrays.asList("Java", "HTML", "CSS", "JavaScript"));
        rec.postJob(portal, "Data Analyst", Arrays.asList("SQL", "Python", "Tableau", "Excel"));
        rec.postJob(portal, "DevOps Engineer", Arrays.asList("Docker", "Kubernetes", "Linux", "Java"));
        
        System.out.println("✓ Total jobs posted: " + portal.getAllJobs().size() + "\n");
        
        // ============ SEARCH JOBS (Linear Search) ============
        System.out.println("🔍 JOB SEARCH (Linear Search - O(n*m))");
        System.out.println("-".repeat(70));
        
        List<Job> javaJobs = portal.searchJobs("Java");
        System.out.println("Jobs requiring skill 'Java': " + javaJobs.size());
        for (Job job : javaJobs) {
            System.out.println("  - " + job.title);
        }
        
        List<Job> sqlJobs = portal.searchJobs("SQL");
        System.out.println("\nJobs requiring skill 'SQL': " + sqlJobs.size());
        for (Job job : sqlJobs) {
            System.out.println("  - " + job.title);
        }
        System.out.println();
        
        // ============ 4️⃣ SKILL-BASED ANALYSIS MODULE ⭐ ============
        System.out.println("4️⃣  SKILL-BASED ANALYSIS MODULE - OPPORTUNITY ANALYSIS ⭐");
        System.out.println("-".repeat(70));
        
        Job job1 = portal.getAllJobs().get(0); // Senior Software Engineer
        System.out.println("\n📌 Job: " + job1.title);
        System.out.println("   Required Skills: " + job1.requiredSkills + "\n");
        
        SkillAnalyzer.analyze("alice", js1.skills, job1.title, job1.requiredSkills);
        System.out.println();
        SkillAnalyzer.analyze("bob", js2.skills, job1.title, job1.requiredSkills);
        System.out.println();
        SkillAnalyzer.analyze("charlie", js3.skills, job1.title, job1.requiredSkills);
        
        // ============ CANDIDATE SHORTLISTING (Sorting - O(n log n)) ============
        System.out.println("\n📊 CANDIDATE SHORTLISTING (Sorting by Match % - O(n log n))");
        System.out.println("-".repeat(70));
        
        List<JobSeeker> candidates = new ArrayList<>(Arrays.asList(js1, js2, js3));
        candidates = rec.shortlistCandidates(candidates, job1.requiredSkills);
        
        System.out.println("Candidates ranked for: " + job1.title);
        int rank = 1;
        for (JobSeeker js : candidates) {
            double match = SkillAnalyzer.calculateMatch(js.skills, job1.requiredSkills);
            System.out.println("  " + rank + ". " + js.username + " - " + String.format("%.2f", match) + "% match");
            rank++;
        }
        System.out.println();
        
        // ============ 5️⃣ ADMIN MODULE ============
        System.out.println("5️⃣  ADMIN MODULE (User Management & Verification)");
        System.out.println("-".repeat(70));
        
        Admin admin = new Admin("admin1", "adminpw");
        admin.manageUsers(registry);
        
        Job testJob = new Job("Test Job", Arrays.asList("Java"));
        System.out.println("  Verify job posting: " + (admin.verifyJob(testJob) ? "Valid ✓" : "Invalid ✗"));
        System.out.println();
        
        // ============ 6️⃣ DSA DEMONSTRATIONS ============
        System.out.println("6️⃣  DATA STRUCTURES & ALGORITHMS DEMONSTRATIONS");
        System.out.println("-".repeat(70));
        
        // -------- LinkedList (ADT) --------
        System.out.println("\n✦ CUSTOM LINKED LIST (Singly Linked - O(1) insert)");
        LinkedList skillsList = new LinkedList();
        skillsList.insert("Kubernetes");
        skillsList.insert("SQL");
        skillsList.insert("Spring");
        skillsList.insert("DSA");
        skillsList.insert("Java");
        System.out.print("  Skills (Linked List):");
        skillsList.traverse();
        System.out.println("  Operations: insert O(1), traverse O(n)");
        
        // -------- Stack --------
        System.out.println("\n✦ STACK - Job Processing (LIFO - O(1) push/pop)");
        Stack<Job> jobStack = new Stack<>();
        jobStack.push(new Job("ML Engineer", Arrays.asList("Python", "TensorFlow")));
        jobStack.push(new Job("Cloud Architect", Arrays.asList("AWS", "Docker")));
        jobStack.push(new Job("Frontend Developer", Arrays.asList("React", "JavaScript")));
        System.out.println("  Pushed 3 jobs to stack");
        System.out.println("  Processing job (LIFO): " + jobStack.pop().title);
        System.out.println("  Remaining in stack: " + jobStack.size());
        
        // -------- Queue --------
        System.out.println("\n✦ QUEUE - Job Notifications (FIFO - O(1) add/remove)");
        Queue<Job> jobQueue = new LinkedList<>();
        jobQueue.add(new Job("Frontend Dev", Arrays.asList("JS", "React")));
        jobQueue.add(new Job("Backend Dev", Arrays.asList("Java", "Node")));
        jobQueue.add(new Job("QA Engineer", Arrays.asList("Testing", "Automation")));
        System.out.println("  Added 3 jobs to queue");
        System.out.println("  Next notification (FIFO): " + jobQueue.poll().title);
        System.out.println("  Remaining in queue: " + jobQueue.size());
        
        // -------- PriorityQueue --------
        System.out.println("\n✦ PRIORITY QUEUE - Top Candidates (O(log n) operations)");
        PriorityQueue<JobSeeker> pq = new PriorityQueue<>(Comparator.comparingDouble(
                js -> SkillAnalyzer.calculateMatch(js.skills, Arrays.asList("Java", "DSA", "Spring"))
        ).reversed());
        pq.add(js1);
        pq.add(js2);
        pq.add(js3);
        System.out.println("  Top candidate for 'Java/DSA/Spring': " + pq.peek().username);
        System.out.print("  Rankings: ");
        while (!pq.isEmpty()) {
            System.out.print(pq.poll().username + " ");
        }
        System.out.println();
        
        // -------- HashMap --------
        System.out.println("\n✦ HASH TABLE - Fast User Lookup (HashMap - O(1) average)");
        HashMap<String, JobSeeker> seekerMap = new HashMap<>();
        seekerMap.put(js1.username, js1);
        seekerMap.put(js2.username, js2);
        seekerMap.put(js3.username, js3);
        System.out.println("  Stored " + seekerMap.size() + " job seekers in HashMap");
        System.out.println("  Lookup 'alice': " + (seekerMap.get("alice") != null ? "Found ✓" : "Not found ✗"));
        System.out.println("  Lookup 'david': " + (seekerMap.get("david") != null ? "Found ✓" : "Not found ✗"));
        
        // -------- Bubble Sort Example --------
        System.out.println("\n✦ BUBBLE SORT - Sort by Experience (O(n²))");
        List<String> experiences = new ArrayList<>(Arrays.asList("2Y", "1.5Y", "1Y", "3Y"));
        bubbleSort(experiences);
        System.out.println("  Sorted experiences: " + experiences);
        
        // -------- Binary Search (Searching) --------
        System.out.println("\n✦ LINEAR SEARCH - Find Skill (O(n))");
        List<String> allSkills = new ArrayList<>(Arrays.asList("Java", "Python", "SQL", "Docker", "Kubernetes"));
        Collections.sort(allSkills);
        System.out.println("  Skills list: " + allSkills);
        int idx = linearSearch(allSkills, "Docker");
        System.out.println("  Linear search for 'Docker': " + (idx >= 0 ? "Found at index " + idx : "Not found"));
        
        // ============ COMPLEXITY ANALYSIS ============
        System.out.println("\n\n" + "=".repeat(70));
        System.out.println("  📈 TIME & SPACE COMPLEXITY ANALYSIS");
        System.out.println("=".repeat(70));
        
        System.out.println("\n| Data Structure/Algorithm     | Time Complexity | Space Complexity |");
        System.out.println("|------------------------------|-----------------|------------------|");
        System.out.println("| HashMap (lookup)             | O(1) avg        | O(n)             |");
        System.out.println("| ArrayList (access)           | O(1)            | O(n)             |");
        System.out.println("| LinkedList (insert at head)  | O(1)            | O(n)             |");
        System.out.println("| Stack (push/pop)             | O(1)            | O(n)             |");
        System.out.println("| Queue (add/remove)           | O(1)            | O(n)             |");
        System.out.println("| PriorityQueue (add)          | O(log n)        | O(n)             |");
        System.out.println("| Linear Search                | O(n)            | O(1)             |");
        System.out.println("| Bubble Sort                  | O(n²)           | O(1)             |");
        System.out.println("| Merge/Quick Sort             | O(n log n)      | O(n)             |");
        System.out.println("| Hash Table (search)          | O(1) avg        | O(n)             |");
        System.out.println();
        
        System.out.println("=".repeat(70));
        System.out.println("    ✅ DEMO COMPLETE - All modules working successfully!");
        System.out.println("=".repeat(70) + "\n");
    }
    
    // ============ HELPER METHODS ============
    
    // Bubble Sort - O(n²)
    static void bubbleSort(List<String> list) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (list.get(j).compareTo(list.get(j + 1)) > 0) {
                    String temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
    }
    
    // Linear Search - O(n)
    static int linearSearch(List<String> list, String target) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(target)) {
                return i;
            }
        }
        return -1;
    }
}