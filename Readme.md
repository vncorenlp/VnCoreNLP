# VnCoreNLP: A Vietnamese natural language processing toolkit

VnCoreNLP is a Java NLP annotation pipeline for Vietnamese, providing rich linguistic annotations through key NLP components of *word segmentation*, *POS tagging*, *named entity recognition* (NER) and *dependency parsing*:

* **Accurate** – VnCoreNLP components obtain higher results than all previous published results on standard benchmark datasets.
* **Fast** – VnCoreNLP is fast, so it can be used for dealing with large-scale data. Also it benefits users suffering from limited computation resources (e.g. users from Vietnam).
* **Easy-to-use** – VnCoreNLP is wrapped into a single `.jar` file, so users do not have to install external dependencies. Users can run processing pipelines from either the command-line or the Java API.

The general architecture and experimental results of VnCoreNLP can be found in the following related papers:
1. Thanh Vu, Dat Quoc Nguyen, Dai Quoc Nguyen, Mark Dras and Mark Johnson. **2018**. VnCoreNLP: A Vietnamese Natural Language Processing Toolkit. [.pdf]
2. Dat Quoc Nguyen, Dai Quoc Nguyen, Thanh Vu, Mark Dras and Mark Johnson. **2018**. A Fast and Accurate Vietnamese Word Segmenter. In *Proceedings of the 11th International Conference on Language Resources and Evaluation*, LREC 2018, to appear. [[.pdf]](https://arxiv.org/pdf/1709.06307.pdf)
3. Dat Quoc Nguyen, Thanh Vu, Dai Quoc Nguyen, Mark Dras and Mark Johnson. **2017**. From Word Segmentation to POS Tagging for Vietnamese. In *Proceedings of the 15th Annual Workshop of the Australasian Language Technology Association*, ALTA 2017, pages 108-113. [[.pdf]](https://arxiv.org/pdf/1711.04951.pdf)

Please cite paper [1] whenever VnCoreNLP is used to produce published results or incorporated into other software. If you are dealing in depth with either word segmentation or POS tagging, you are encouraged to also cite paper [2] or [3], respectively. 

VnCoreNLP is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.

## Using VnCoreNLP from the command line

Assume that Java 1.8+ is already set to run in the command line or terminal (for example: adding Java to the  environment variable `path` in Windows OS); and file  `VnCoreNLP-1.0.jar` (27MB) and folder `models` (113MB) are placed in the same working folder. You can run VnCoreNLP to annotate an input raw text corpus (e.g. a collection of news content) by using following commands:

    //To perform word segmentation, POS tagging, NER and then dependency parsing
    $ java -Xmx2g -jar VnCoreNLP-1.0.jar -fin input.txt -fout output.txt
    // To perform word segmentation, POS tagging and then NER
    $ java -Xmx2g -jar VnCoreNLP-1.0.jar -fin input.txt -fout output.txt -annotators wseg,pos,ner
    // To perform word segmentation and then POS tagging
    $ java -Xmx2g -jar VnCoreNLP-1.0.jar -fin input.txt -fout output.txt -annotators wseg,pos
    // To perform word segmentation
    $ java -Xmx2g -jar VnCoreNLP-1.0.jar -fin input.txt -fout output.txt -annotators wseg    

If you are looking for light-weight versions, VnCoreNLP's word segmentation and POS tagging components have also been released as independent packages [RDRsegmenter](https://github.com/datquocnguyen/RDRsegmenter) (0.3MB) and [VnMarMoT](https://github.com/datquocnguyen/VnMarMoT) (30MB), resepectively.

## Using VnCoreNLP from the API

The following code is a simple and complete example:

```java
import vn.pipeline.*;
import java.io.*;
public class VnCoreNLPExample {
    public static void main(String[] args) throws IOException {
    
        // "wseg", "pos", "ner", and "parse" refer to as word segmentation, POS tagging, NER and dependency parsing, respectively. 
        String[] annotators = {"wseg", "pos", "ner", "parse"}; 
        VnCoreNLP pipeline = new VnCoreNLP(annotators); 
    
        String str = "Ông Nguyễn Khắc Chúc  đang làm việc tại Đại học Quốc gia Hà Nội. Bà Lan, vợ ông Chúc, cũng làm việc tại đây"; 
        Annotation annotation = new Annotation(str); 
        pipeline.annotate(annotation); 
        
        System.out.println(annotation.toString());
        // 1    Ông                 Nc  O       4   sub 
        // 2    Nguyễn_Khắc_Chúc    Np  B-PER   1   nmod
        // 3    đang                R   O       4   adv
        // 4    làm_việc            V   O       0   root
        // ...
        
        //Write to file
        PrintStream outputPrinter = new PrintStream("output.txt");
        pipeline.printToFile(annotation, outputPrinter); 
    
        // You can also get a single sentence to analyze individually 
        Sentence firstSentence = annotation.getSentences().get(0);
        System.out.println(firstSentence.toString());
    }
}
```

See VnCoreNLP's open-source in folder `src` for API details. 