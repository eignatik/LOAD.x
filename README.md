[ ![Codeship Status for eignatik/NGLoad](https://app.codeship.com/projects/b2b34220-8fd1-0135-dea1-4e7f25354534/status?branch=develop)](https://app.codeship.com/projects/250072)

# LOAD.x - version 0.1
Application for load tests. It provides lightweight application for load testing web-sites in real time. You can get statistics of requests in a flexible way via special minimalistic web GUI.

The general advantage is lightweight and dynamical random loading on the given website. The idea is to create a simple application that is going to test your web-site weak sides with just a few clicks.

You will be able to check a loading rate of your site, availability of all links, detailed graphical statistics and metrics. 

**Main difference with other loading tools** - the application automatically parses given websites and constructs the map of the site. Then using some internal algorithms it creates equally distributed loading on the whole website based on parsed links.

P.S. :*If you need to manually set up every single request or to test using complex user scenarios, I would recommend you to check tools such as JMeter or Gatling which have flexible setup for scenarios and screening tools as well. This project is aiming for quick loading tests that are performed automatically.*

## How to use?
The web GUI will be available for local running to operate with loading. 
The results of all loading are saved in database and always can be saved, backed-up and so on.

# History
## Version 0.0.1 Release Notes
- created console dummy implementation *that is fully deprecated and already decommissioned*. 
