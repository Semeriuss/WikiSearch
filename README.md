
# WikiSearch

A simple full-text search implementation in Kotlin on a wikipedia dump.

## Getting Started

### Dependencies

* [smile-nlp-kt](https://github.com/londogard/smile-nlp-kt#smile-nlp-kt) for Stemming words.
* [xpp3](https://mvnrepository.com/artifact/xpp3/xpp3/1.1.4c) for parsing xml.

### Example console output
```
Loaded 661933 documents from C:\data\enwiki-latest-abstract1.xml.gz

Indexing took 58241 ms

Found 53 results for query: machine learning
Document(id=32845, title=Wikipedia: Reinforcement learning, brief=Reinforcement learning (RL) is an area of machine learning ..., url=https://en.wikipedia.org/wiki/Reinforcement_learning, frequencyMap={reinforc=3, ...})
Document(id=42321, title=Wikipedia: Boosting (machine learning), brief=In machine learning, boosting is an ensemble meta-algorithm..., url=https://en.wikipedia.org/wiki/Boosting_(machine_learning), frequencyMap={boost=4, ...})
Document(id=10225, title=Wikipedia: Supervised learning, brief=Supervised learning (SL) is a machine learning paradigm ..., url=https://en.wikipedia.org/wiki/Supervised_learning, frequencyMap={supervis=3, ...})
.
.
.
Search took 207 ms

```

## Author

Semere Habtu
[se.semere.habtu@gmail.com](Email)

## Version History
* 0.1
    * Initial Release

## License

This project is licensed under the [MIT] License - see the LICENSE.md file for details

## Acknowledgments
* [Bart de Goede](https://bart.degoe.de/building-a-full-text-search-engine-150-lines-of-code/) - Python implementation.
* [Artem Krylysov](https://artem.krylysov.com/blog/2020/07/28/lets-build-a-full-text-search-engine/) - Go implementation.
* [Hampus Londögård](https://github.com/londogard/smile-nlp-kt) - NLP toolkits for Kotlin.
* [Wikimedia](https://dumps.wikimedia.org/) - A complete copy of all Wikimedia wikis, in the form of wikitext source and metadata embedded in XML. 
