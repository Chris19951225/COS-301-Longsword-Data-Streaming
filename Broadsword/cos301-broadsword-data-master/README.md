# cos301-broadsword-data
Note that to see the agile workflow with kanban cards, you need zenhub. Checkout wip for the most recent work.
## Worflow conventions:
- Take work from the backlog and when completed move it to review. Then a team mate will review it and move it to done and close the issue. Rinse and repeat.
- Feature freeze commences on 18 Apr 00:00
- Total freeze commences on 19 Apr 00:00
- Bleeding edge work goes into the wip branch
- If a feature is completed merge into feature
- Stable versions get merged into master
- If a bug needs fixing merge into bug, fix it and merge back in to the branch where the bug occured
- Epics contain mutiple stories that need to be completed for each milestone
## A message to integration
For the documentation, there is a mock main.tex, but that is just to check that everything compiles. There is a makefile for the documentation that compiles the diagrams that are included in the latex section of our subsystem. The software dependency(Umlet) can be satisfied by running `make dependency`. The document can be compiled by running `make pdf` and if you just want to compile the diagrams run `make diagrams`.

To satisfy the few software dependencies that there are, run `./integration_setup.sh`.

Lastly, the entry point to the system is query\_resolver.py. If you run `query_resolver.py -h`, it will print out the program parameters that need filling in for the system to function as expected. These parameters include the NSQ server location, port number and Aruba credentials to name a few.
