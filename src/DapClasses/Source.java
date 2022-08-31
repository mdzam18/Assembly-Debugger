package src.DapClasses;

import src.DapClasses.Capabilities.Checksum;

public class Source {
    private String name;
    private String path;
    private int sourceReference;
    private String presentationHint;
    private String origin;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getSourceReference() {
        return sourceReference;
    }

    public void setSourceReference(int sourceReference) {
        this.sourceReference = sourceReference;
    }

    public String getPresentationHint() {
        return presentationHint;
    }

    public void setPresentationHint(String presentationHint) {
        this.presentationHint = presentationHint;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Source[] getSources() {
        return sources;
    }

    public void setSources(Source[] sources) {
        this.sources = sources;
    }

    public Object getAdapterData() {
        return adapterData;
    }

    public void setAdapterData(Object adapterData) {
        this.adapterData = adapterData;
    }

    public Checksum[] getChecksums() {
        return checksums;
    }

    public void setChecksums(Checksum[] checksums) {
        this.checksums = checksums;
    }

    private Source[] sources;
    private Object adapterData;
    private Checksum[] checksums;
}
