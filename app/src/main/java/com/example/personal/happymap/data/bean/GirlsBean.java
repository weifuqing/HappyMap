package com.example.personal.happymap.data.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by dell on 2016/8/8.
 */
public class GirlsBean {

    private boolean error;

    private List<ResultEntity> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ResultEntity> getResults() {
        return results;
    }

    public void setResults(List<ResultEntity> results) {
        this.results = results;
    }

    public static class ResultEntity implements Parcelable{

        private String _id;
        private String createdAt;
        private String desc;
        private String publishedAt;
        private String source;
        private String type;
        private String url;
        private boolean used;
        private String who;

        public String getWho() {
            return who;
        }

        public void setWho(String who) {
            this.who = who;
        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isUsed() {
            return used;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public static final Creator<ResultEntity> CREATOR = new Creator<ResultEntity>() {
            @Override
            public ResultEntity createFromParcel(Parcel in) {
                return new ResultEntity(in);
            }

            @Override
            public ResultEntity[] newArray(int size) {
                return new ResultEntity[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int i) {

            dest.writeString(this._id);
            dest.writeString(this.createdAt);
            dest.writeString(this.desc);
            dest.writeString(this.publishedAt);
            dest.writeString(this.source);
            dest.writeString(this.type);
            dest.writeString(this.url);
            dest.writeByte(this.used ? (byte) 1 : (byte) 0);
            dest.writeString(this.who);
        }

        protected ResultEntity(){}

        protected ResultEntity(Parcel in){
            this._id = in.readString();
            this.createdAt = in.readString();
            this.desc = in.readString();
            this.publishedAt = in.readString();
            this.source = in.readString();
            this.type = in.readString();
            this.url = in.readString();
            this.used = in.readByte() != 0;
            this.who = in.readString();
        }

    }
}
