# Lab Session 1
## An introduction to our environment

  * _Note_: This file includes markup. Until we incorporate .md markup, [you can read the html version here] (https://gist.github.com/nvasilakis/8605902#file-lab1-md)

## Plan for today

  * 10' VM/Linux setup and issues

  * 30' Shell (bash, scripting, zsh, screen, environment)

  * 20' versioning (svn, git-svn)

  * 10' eclipse intro / Q&A session

  * Big take-aways from the course include  learning to work with big
    code-bases, study and follow specifications, system design etc. As a
    side-effect, you gain some skills in _software engineering_
    (versioning, designing, testing) and _system administration_
    (Unix/Linux, scripting, environment, etc.)

  * Some of the things we'll see in the lab  sessions will be
    immediately useful, some of  them a bit late, some of them probably
    in your last project (setting up amazon EC2 instances) -- and a tiny
    bit much later

  * In this lab session today, I'll try to bring people with different background on
    the same page -- I apologize to students that know some of these
    already.

  * Let me know what you find interesting and what you don't, so we can
    adjust sessions accordingly!

## Linux

  * We are running Ubuntu (a debian-based distribution) with a default
    gnome window manager and dpkg/apt-get package manager. To install most 
    of the tools, you use apt-get (modulo some things that are not in the 
    repositories). For instance, to grab chrome:

      `sudo apt-get install chromium-browser`

    If you don't know the name of the package, the apt-cache will help:

      `apt-cache search chrome`

    even better

      `apt-cache search chrome | grep browser # more on grep later`

    In case you need more details

      `apt-cache show chromium-browser`

    _Note:_ This will be different from other systems. For instance EC2
    default Linux uses a heavily-modified fedora/RHEL-based distro; as a
    result, it makes use of a _different_ package manager

  * The default tool-chain comprises of 

      `ec2-api-tools zip unzip eclipse subversion libsvn-java
      apache2-utils build-essential curl`

  * You can obviously change key-bindings, applications, browsers, everything
      > For instance, I use zsh instead of bash for my shell

  * If you have *serious* performance problems, there are two options:

    1. Picking a lighter environment will help (window manager and applications)
    2. Setting up a separate partition on your machine to install Ubuntu.

# Shell -- Usage

  * Before versioning, let's spend some time in the shell. It will help
    you accomplish tasks faster (well, especially after you've invested
    a tiny bit of time!)

  * Your default shell is Bash (Bourne Again SHell), so when you start
    the "terminal emulator" (e.g., xterm, gnome-terminal) this invokes
    also your default shell (in /etc/passwd, changed by chsh). To start
    _a_ shell instance, e.g. bash, run:

      `bash [[_option_] [_file_]]`

    * [file] can be a script, for non-interactive mode (more on this later, usefull for EC2)
    * [option] can be anything: -x, -v are useful for debugging, -c to read string next

  * First, to get initialized according to your preferences it reads the following
    files in the following order (i.e., next overwrites previous)

    * /etc/profile
    * ~/.bash_profile
    * ~/.bash_login
    * ~/.profile
    * ~/.bashrc   <---- That's the one to rule them all!

  * In interactive mode, you type commands, which are programs in /bin
    or /usr/bin or _whatever is your PATH_ environmental variable

      `ls`

  * Output comes out of stdout or stderr, input gets in from stdin

  * Shell expansion: a feature where the shell "expands" your
    meta-character to whatever matches, based on some rules (what?)
    Example meta-characters include:

      * * -- everything under the sun
      * ? -- everything und.. but only one character!
      * [ab-e...]  -- any one character drawn from this set/range
      * [!abc...]  -- invert the above
      * {s1,s2,s3,...} -- expand to: s1 s2 s3 (_NO SPACE!_)
      * ~user         -- replaced by user (or named directory)
      * ! -- Does history expansion 
          * !! -- last command
          * !3 -- command #3 in history (`history` gives numbers, * `history | grep "string"` searches for string in history)
          * !cmd -- latest _cmd_ instance
          * !x:y -- _y_ argument of _x_ command (`!!:3`)

      > Examples.. (live) files, renaming, find

  * Environmental variables (env): think of them as a (hash-)map from
    variables to values, available in both the shell and the programs
    invoked from there. Example variables include:
      * HOME -- user's home directory
      * PATH -- where to look for executables

      > Examples.. (live) setenv for production/testing environment


  * To help with the examples, tips and tricks on editing (emacs mode):

    * Crtl-B (Meta) -- back 
    * Crtl-F (Meta) -- fwd
    * Ctrl-A (Meta) -- abegin:P
    * Ctrl-E (Meta) -- end
    * Ctrl-D (Meta) -- delete
    * ---
    * Ctrl-K -- "kill" start 
    * Ctrl-U -- "kill" end 
    * ---
    * Ctrl-P -- previous history
    * Ctrl-N -- next history 
    * ---
    * Ctrl-L (clear)

  * Intermediate features (live)

    * #  -- comments
    * () -- (subshell)
    * {} -- code block (same shell)
    * &  -- background 
    * |  -- pipe (chain commands together)
    * > < >> -- redirection (2>&1 used often)
    * there are more..

    > Examples (live) start eclipse in background, redirect from ant
    
  * Variables need prefix $ to "name"them, i.e. $PATH, $my_user, $0.
    There are also some "special" variables, mostly used in shell
    scripting:

    * $n -- argument n ($0 is script name)
    * $# -- number of arguments
    * $* -- all arguments
    * $$ -- process id (if you want to kill)
    * $? -- error code (0 for success) of previous command (it's tricky!)

  * This brings us to process management

    * &  -- _starts_ a process in the background
    * Ctrl-Z -- sends a STOP signal to a process
    * bg/fg -- sends an existing process to the background/foreground
    * jobs  -- shows existing processes

  * The shell has much more that we did not cover:

    * loop and control statements (if, case, while, for, test)
    * built-in commands (echo, alias, eval, exit)
    * functions (function nu { })
    * completion etc.
    * prompt

  * For more you can browse online:
  
    * The [Advanced Bash Scripting Guide]
      (http://tldp.org/LDP/abs/html/), about the art of shell scripting! (Basics chapter is more than enough for this course)
      `

    * [github] (http://github.com), [googlecode] (http://code.google.com), [bitbucket] (http://bitbucket.org) are great places to read other
      people's code and learn!

    * My setup shown here, including screen, zsh and vim,  is also available online ([shell](https://github.com/nvasilakis/dotrc), [vim] (http://github.com/nvasilakis/immateriia), [emacs] (http://github.com/nvasilakis/.emacs.d) )


## Shell -- Tools

  * Now all of the above is (almost) useless without programs -- some
    are built-in (history, echo) and most are installed in the PATH.

  * Each executable takes a number of options (-l -x) or arguments (-f
    [file]). For instance, `ls -l`, `ls -a`, `ls -la`, `ls -laF`

    > Examples (live) .. get files by date, alphabetically or size
  
  * Examples of tools that you might need for the course:
    * Basic -- ls, mkdir, rm, firefox, eclipse, apt-get
    * Advanced -- screen, zsh
    * Development -- ant, ab, javac, svn, git
    * Editors - vim, emacs, joe, nedit

    > Examples (live) .. showcase rm, screen, ant, vim/emacs

  * Mini suggestions

    * I use `screen` to multiplex my working environment (debugging output etc.)
    * Use `zsh` for completion -- right prompt is also handy!

  * Some commands that alter system-wide components need super-user
    authorization. This is achieved by the sudo command:
      * `sudo apt-get install screen zsh`
      * You can use apt-get to install everything, 
      * Virtual machine password is vm, 
      >  you can change with passwd (you don't need though)

## Subversion

  * cheat-sheet, all the commands you will ever need (prefix is svn):
    * `add` -- add files
    * `checkout (co)` -- fresh checkout from the repo, once
    * `commit (ci)` -- commit your changes to the server
    * `diff (di)` -- view the differences
    * `revert` -- revert a file back to were it was (you may want to keep a copy)
    * `status (stat, st)` -- find the current status
    * `update (up)` -- update your working directory, fetching changes from the server
    * `log` -- check what happened earlier
    * _help_ (?, h) -- the ubiquitous help

  * The usual life-cycle, after a checkout (notice, co is for checkout, not commit!)
    
              +------------------<-------------------+
              +--> edit files -> update -> commit -->+

  * To update to a specific version
    `svn update -r123`

  * To update/commit a specific file/directory
    `svn update/commit <dir/file>`

  * Set up your editor for the commit messages with EDITOR="your-favorite-editor",
    * write good commit messages
    * short one-liner (72 chars), next line empty
    * 2-3 sentences or bullet points for your changes

  * More Tips: 
    * commit often, serves both as a backup and as a practice that
      helps you test and make concious progress (I usually commit every
      1-2 hours). 
    * Keep each change coherent and self-contained. You might
      need to role back.
    * Add a pre-commit hooks (i.e., run a full compilation, unit tests etc.)
    * Use svnignore to ignore files you don't want to commit
    * Again, write good commit messages!

## git-svn
  
  * People in class have asked if they can use git with their assignments;
    unfortunately we have already set up everything on svn. _But_ with
    git-svn, you can use git in the back-end and use git-svn to
    interface with the server
    * local commits
    * fast branches
    * rebases/history amendments before pushing
    * _Note:_ It will be slower, because the server is ignorant of git

  * "Checkout" (clone in git-speak)
    * `git-svn clone <url>`
    * with -s grabs standardized layout (you probably don't need it)
    * create a .gitignore based on your .svnignore
    
  * Workflow is now edit, (git add,) git commit..(loop)

  * To interface with the server you:  (!important!)
    * `git-svn dcommit` to push changes (_it's not git push_)
    * `git-svn rebase` to pull changes (linear history, _it's not git pull/rebase_)

  * You can use all the usual git goodies:
    * `git commit` -- to commit locally
    * `git checkout -b <branch>` -- to switch to a new branch
    * `git merge ` -- to merge two branches
    * `git -ip ` -- for interactive adding
    * `git bisect` -- to trace down problems
    * `git whatchanged/blame` -- to see what/who introduced a change
    * `git stash push/pop` -- to stash changes locally

## Eclipse hands-on and VM-related

  * Question asked: How to ssh/rsync to the Virtual Machine from the
    host OS:
    1. Install ssh server: `sudo apt-get install openssh-server`
    2. Run `ifonfig` to find out network address
    3. Run `ssh cis455@<ip>` to connect

  * Q: Why is ant pack failing:
    1. Make sure you don't already have a submit*.zip in your folder by
       running `ls`
    2. Remove it `rm submit*zip`
    3. Run `ant pack` again


## Round up 

  * Make sure to have fun! 
    _This is one of the coolest courses I've ever taken_

## Contact
  * Use piazza page as much as possible
  * In case anyone needs it: [nvas@seas.upenn.edu][] (also on the course page)
